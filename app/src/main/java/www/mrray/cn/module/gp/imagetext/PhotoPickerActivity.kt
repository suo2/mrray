package www.mrray.cn.module.gp.imagetext

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.database.Cursor
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.support.v4.app.LoaderManager
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.ListPopupWindow
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_photopicker.*
import www.mrray.cn.R
import www.mrray.cn.module.gp.imagetext.intent.PhotoPreviewIntent
import www.mrray.cn.utils.RxPermissionManager
import java.io.File
import java.io.IOException
import java.util.*

/**
 * @author suo
 * @date 2018/10/22
 * @desc: 图片编辑
 */
class PhotoPickerActivity : AppCompatActivity() {

    private var mCxt: Context? = null

    // 结果数据
    private var resultList: ArrayList<String>? = ArrayList()
    // 文件夹数据
    private val mResultFolder = ArrayList<Folder>()

    private var menuDoneItem: MenuItem? = null
    private var mPopupAnchorView: View? = null

    // 最大照片数量
    private var captureManager: ImageCaptureManager? = null
    private var mDesireImageCount: Int = 0
    private var imageConfig: ImageConfig? = null // 照片配置

    private var mImageAdapter: ImageGridAdapter? = null
    private var mFolderAdapter: FolderAdapter? = null
    private var mFolderPopupWindow: ListPopupWindow? = null

    private var hasFolderGened = false
    private var mIsShowCamera = false


    private val mLoaderCallback = object : LoaderManager.LoaderCallbacks<Cursor> {

        private val IMAGE_PROJECTION = arrayOf(MediaStore.Images.Media.DATA, MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.DATE_ADDED, MediaStore.Images.Media._ID)

        override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {

            // 根据图片设置参数新增验证条件
            val selectionArgs = StringBuilder()

            if (imageConfig != null) {
                if (imageConfig!!.minWidth != 0) {
                    selectionArgs.append(MediaStore.Images.Media.WIDTH + " >= " + imageConfig!!.minWidth)
                }

                if (imageConfig!!.minHeight != 0) {
                    selectionArgs.append(if ("" == selectionArgs.toString()) "" else " and ")
                    selectionArgs.append(MediaStore.Images.Media.HEIGHT + " >= " + imageConfig!!.minHeight)
                }

                if (imageConfig!!.minSize.toFloat() != 0f) {
                    selectionArgs.append(if ("" == selectionArgs.toString()) "" else " and ")
                    selectionArgs.append(MediaStore.Images.Media.SIZE + " >= " + imageConfig!!.minSize)
                }

                if (imageConfig!!.mimeType != null) {
                    selectionArgs.append(" and (")
                    var i = 0
                    val len = imageConfig!!.mimeType!!.size
                    while (i < len) {
                        if (i != 0) {
                            selectionArgs.append(" or ")
                        }
                        selectionArgs.append(MediaStore.Images.Media.MIME_TYPE + " = '" + imageConfig!!.mimeType!![i] + "'")
                        i++
                    }
                    selectionArgs.append(")")
                }
            }

            if (id == LOADER_ALL) {
                return CursorLoader(mCxt!!,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                        selectionArgs.toString(), null, IMAGE_PROJECTION[2] + " DESC")
            } else if (id == LOADER_CATEGORY) {
                var selectionStr = selectionArgs.toString()
                if ("" != selectionStr) {
                    selectionStr += " and$selectionStr"
                }
                return CursorLoader(mCxt!!,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                        IMAGE_PROJECTION[0] + " like '%" + args!!.getString("path") + "%'" + selectionStr, null,
                        IMAGE_PROJECTION[2] + " DESC")
            }

            return CursorLoader(mCxt!!,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                    IMAGE_PROJECTION[0] + " like '%" + args!!.getString("path") + "%'" , null,
                    IMAGE_PROJECTION[2] + " DESC")
        }

        override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
            if (data != null) {
                val images = ArrayList<Image>()
                val count = data.count
                if (count > 0) {
                    data.moveToFirst()
                    do {
                        val path = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]))
                        val name = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]))
                        val dateTime = data.getLong(data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]))

                        val image = Image(path, name, dateTime)
                        images.add(image)
                        if (!hasFolderGened) {
                            // 获取文件夹名称
                            val imageFile = File(path)
                            val folderFile = imageFile.parentFile
                            val folder = Folder()
                            folder.name = folderFile.name
                            folder.path = folderFile.absolutePath
                            folder.cover = image
                            if (!mResultFolder.contains(folder)) {
                                val imageList = ArrayList<Image>()
                                imageList.add(image)
                                folder.images = imageList
                                mResultFolder.add(folder)
                            } else {
                                // 更新
                                val f = mResultFolder[mResultFolder.indexOf(folder)]
                                f.images!!.add(image)
                            }
                        }

                    } while (data.moveToNext())

                    mImageAdapter!!.setData(images)

                    // 设定默认选择
                    if (resultList != null && resultList!!.size > 0) {
                        mImageAdapter!!.setDefaultSelected(resultList!!)
                    }

                    mFolderAdapter!!.setData(mResultFolder)
                    hasFolderGened = true

                }
            }
        }

        override fun onLoaderReset(loader: Loader<Cursor>) {

        }
    }

    /**
     * 获取GridView Item宽度
     * @return
     */
    private val itemImageWidth: Int
        get() {
            val cols = numColnums
            val screenWidth = resources.displayMetrics.widthPixels
            val columnSpace = resources.getDimensionPixelOffset(R.dimen.space_size)
            return (screenWidth - columnSpace * (cols - 1)) / cols
        }

    /**
     * 根据屏幕宽度与密度计算GridView显示的列数， 最少为三列
     * @return
     */
    private val numColnums: Int
        get() {
            val cols = resources.displayMetrics.widthPixels / resources.displayMetrics.densityDpi
            return if (cols < 3) 3 else cols
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photopicker)

        initViews()

        // 照片属性
        imageConfig = intent.getParcelableExtra(EXTRA_IMAGE_CONFIG)

        // 首次加载所有图片
        supportLoaderManager.initLoader(LOADER_ALL, null, mLoaderCallback)

        // 选择图片数量
        mDesireImageCount = intent.getIntExtra(EXTRA_SELECT_COUNT, DEFAULT_MAX_TOTAL)

        // 图片选择模式
        val mode = intent.extras!!.getInt(EXTRA_SELECT_MODE, MODE_SINGLE)

        // 默认选择
        if (mode == MODE_MULTI) {
            val tmp = intent.getStringArrayListExtra(EXTRA_DEFAULT_SELECTED_LIST)
            if (tmp != null && tmp.size > 0) {
                // resultList.addAll(tmp);
            }
        }

        // 是否显示照相机
        mIsShowCamera = intent.getBooleanExtra(EXTRA_SHOW_CAMERA, false)
        mImageAdapter = ImageGridAdapter(mCxt!!, mIsShowCamera, itemImageWidth)
        // 是否显示选择指示器
        mImageAdapter!!.showSelectIndicator(mode == MODE_MULTI)
        mGridView!!.adapter = mImageAdapter

        mGridView!!.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            if (mImageAdapter!!.isShowCamera) {
                // 如果显示照相机，则第一个Grid显示为照相机，处理特殊逻辑
                if (i == 0) {
                    if (mode == MODE_MULTI) {
                        // 判断选择数量问题
                        if (mDesireImageCount == resultList!!.size - 1) {
                            Toast.makeText(mCxt, R.string.msg_amount_limit, Toast.LENGTH_SHORT).show()
                            return@OnItemClickListener
                        }
                    }
                    showCameraAction()
                } else {
                    // 正常操作
                    val image = adapterView.adapter.getItem(i) as Image
                    selectImageFromGrid(image, mode)
                }
            } else {
                // 正常操作
                val image = adapterView.adapter.getItem(i) as Image
                selectImageFromGrid(image, mode)
            }
        }

        mFolderAdapter = FolderAdapter(mCxt!!)

        // 打开相册列表
        btnAlbum!!.setOnClickListener {
            if (mFolderPopupWindow == null) {
                createPopupFolderList()
            }

            if (mFolderPopupWindow!!.isShowing) {
                mFolderPopupWindow!!.dismiss()
            } else {
                mFolderPopupWindow!!.show()
                var index = mFolderAdapter!!.selectIndex
                index = if (index == 0) index else index - 1
                mFolderPopupWindow!!.listView!!.setSelection(index)
            }
        }

        // 预览
        btnPreview!!.setOnClickListener {
            val intent = PhotoPreviewIntent(mCxt!!)
            intent.setCurrentItem(0)
            intent.setPhotoPaths(resultList!!)
            startActivityForResult(intent, PhotoPreviewActivity.REQUEST_PREVIEW)
        }

    }

    private fun initViews() {
        mCxt = this
        captureManager = ImageCaptureManager(mCxt!!)
        // ActionBar Setting
        val toolbar = findViewById<View>(R.id.pickerToolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setTitle(resources.getString(R.string.image))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mGridView!!.numColumns = numColnums

        mPopupAnchorView = findViewById(R.id.photo_picker_footer)
    }

    private fun createPopupFolderList() {

        mFolderPopupWindow = ListPopupWindow(mCxt!!)
        mFolderPopupWindow!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mFolderPopupWindow!!.setAdapter(mFolderAdapter)
        mFolderPopupWindow!!.setContentWidth(ListPopupWindow.MATCH_PARENT)
        mFolderPopupWindow!!.width = ListPopupWindow.MATCH_PARENT

        // 计算ListPopupWindow内容的高度(忽略mPopupAnchorView.height)，R.layout.item_foloer
        val folderItemViewHeight =
        // 图片高度
                resources.getDimensionPixelOffset(R.dimen.folder_cover_size) +
                        // Padding Top
                        resources.getDimensionPixelOffset(R.dimen.folder_padding) +
                        // Padding Bottom
                        resources.getDimensionPixelOffset(R.dimen.folder_padding)
        val folderViewHeight = mFolderAdapter!!.count * folderItemViewHeight

        val screenHeigh = resources.displayMetrics.heightPixels
        if (folderViewHeight >= screenHeigh) {
            mFolderPopupWindow!!.height = Math.round(screenHeigh * 0.6f)
        } else {
            mFolderPopupWindow!!.height = ListPopupWindow.WRAP_CONTENT
        }

        mFolderPopupWindow!!.anchorView = mPopupAnchorView
        mFolderPopupWindow!!.isModal = true
        mFolderPopupWindow!!.animationStyle = R.style.Animation_AppCompat_DropDownUp
        mFolderPopupWindow!!.setOnItemClickListener { parent, view, position, id ->
            mFolderAdapter!!.selectIndex = position

            Handler().postDelayed({
                mFolderPopupWindow!!.dismiss()

                if (position == 0) {
                    supportLoaderManager.restartLoader(LOADER_ALL, null, mLoaderCallback)
                    btnAlbum!!.setText(R.string.all_image)
                    mImageAdapter!!.isShowCamera = mIsShowCamera
                } else {
                    val folder = parent.adapter.getItem(position) as Folder
                    if (null != folder) {
                        mImageAdapter!!.setData(folder.images)
                        btnAlbum!!.text = folder.name
                        // 设定默认选择
                        if (resultList != null && resultList!!.size > 0) {
                            mImageAdapter!!.setDefaultSelected(resultList!!)
                        }
                    }
                    mImageAdapter!!.isShowCamera = false
                }

                // 滑动到最初始位置
                mGridView!!.smoothScrollToPosition(0)
            }, 100)
        }
    }

    fun onSingleImageSelected(path: String) {
        val data = Intent()
        resultList!!.add(path)
        data.putStringArrayListExtra(EXTRA_RESULT, resultList)
        setResult(Activity.RESULT_OK, data)
        finish()
    }

    fun onImageSelected(path: String) {
        if (!resultList!!.contains(path)) {
            resultList!!.add(path)
        }
        refreshActionStatus()
    }

    fun onImageUnselected(path: String) {
        if (resultList!!.contains(path)) {
            resultList!!.remove(path)
        }
        refreshActionStatus()
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                // 相机拍照完成后，返回图片路径
                ImageCaptureManager.REQUEST_TAKE_PHOTO -> {
                    if (captureManager!!.currentPhotoPath != null) {
                        captureManager!!.galleryAddPic()
                        resultList!!.add(captureManager!!.currentPhotoPath!!)
                    }
                    complete()
                }
                // 预览照片
                PhotoPreviewActivity.REQUEST_PREVIEW -> {
                    val pathArr = data!!.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT)
                    // 刷新页面
                    if (pathArr != null && pathArr.size != resultList!!.size) {
                        resultList = pathArr
                        refreshActionStatus()
                        mImageAdapter!!.setDefaultSelected(resultList!!)
                    }
                }
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        Log.d(TAG, "on change")

        // 重置列数
        mGridView!!.numColumns = numColnums
        // 重置Item宽度
        mImageAdapter!!.setItemSize(itemImageWidth)

        if (mFolderPopupWindow != null) {
            if (mFolderPopupWindow!!.isShowing) {
                mFolderPopupWindow!!.dismiss()
            }

            // 重置PopupWindow高度
            val screenHeigh = resources.displayMetrics.heightPixels
            mFolderPopupWindow!!.height = Math.round(screenHeigh * 0.6f)
        }

        super.onConfigurationChanged(newConfig)
    }

    /**
     * 选择相机
     */
    private fun showCameraAction() {
        try {
            val intent = captureManager!!.dispatchTakePictureIntent()
            RxPermissionManager.getCameraPermission(this,{
                startActivityForResult(intent, ImageCaptureManager.REQUEST_TAKE_PHOTO)
            },{

            })

        } catch (e: IOException) {
            Toast.makeText(mCxt, R.string.msg_no_camera, Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }

    }

    /**
     * 选择图片操作
     * @param image
     */
    private fun selectImageFromGrid(image: Image?, mode: Int) {
        if (image != null) {
            // 多选模式
            if (mode == MODE_MULTI) {
                if (resultList!!.contains(image.path)) {
                    resultList!!.remove(image.path)
                    onImageUnselected(image.path)
                } else {
                    // 判断选择数量问题
                    if (mDesireImageCount == resultList!!.size) {
                        Toast.makeText(mCxt, R.string.msg_amount_limit, Toast.LENGTH_SHORT).show()
                        return
                    }
                    resultList!!.add(image.path)
                    onImageSelected(image.path)
                }
                mImageAdapter!!.select(image)
            } else if (mode == MODE_SINGLE) {
                // 单选模式
                onSingleImageSelected(image.path)
            }
        }
    }

    /**
     * 刷新操作按钮状态
     */
    private fun refreshActionStatus() {
        if (resultList!!.contains("000000")) {
            resultList!!.remove("000000")
        }
        val text = getString(R.string.done_with_count, resultList!!.size, mDesireImageCount)
        menuDoneItem!!.title = text
        val hasSelected = resultList!!.size > 0
        menuDoneItem!!.isVisible = hasSelected
        btnPreview!!.isEnabled = hasSelected
        if (hasSelected) {
            btnPreview!!.text = resources.getString(R.string.preview) + "(" + resultList!!.size + ")"
        } else {
            btnPreview!!.text = resources.getString(R.string.preview)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_picker, menu)
        menuDoneItem = menu.findItem(R.id.action_picker_done)
        menuDoneItem!!.isVisible = false
        refreshActionStatus()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }

        if (item.itemId == R.id.action_picker_done) {
            complete()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    // 返回已选择的图片数据
    private fun complete() {
        val data = Intent()
        data.putStringArrayListExtra(EXTRA_RESULT, resultList)
        setResult(Activity.RESULT_OK, data)
        finish()
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        captureManager!!.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        captureManager!!.onRestoreInstanceState(savedInstanceState)
        super.onRestoreInstanceState(savedInstanceState)
    }

    companion object {

        val TAG = PhotoPickerActivity::class.java.name

        /** 图片选择模式，int类型  */
        val EXTRA_SELECT_MODE = "select_count_mode"
        /** 单选  */
        val MODE_SINGLE = 0
        /** 多选  */
        val MODE_MULTI = 1
        /** 最大图片选择次数，int类型  */
        val EXTRA_SELECT_COUNT = "max_select_count"
        /** 默认最大照片数量  */
        val DEFAULT_MAX_TOTAL = 9
        /** 是否显示相机，boolean类型  */
        val EXTRA_SHOW_CAMERA = "show_camera"
        /** 默认选择的数据集  */
        val EXTRA_DEFAULT_SELECTED_LIST = "default_result"
        /** 筛选照片配置信息  */
        val EXTRA_IMAGE_CONFIG = "image_config"
        /** 选择结果，返回为 ArrayList&lt;String&gt; 图片路径集合   */
        val EXTRA_RESULT = "select_result"

        // 不同loader定义
        private val LOADER_ALL = 0
        private val LOADER_CATEGORY = 1
    }
}
