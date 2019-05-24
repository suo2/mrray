package www.mrray.cn.module.gp.imagetext

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import www.mrray.cn.R

import java.io.File
import java.util.*

/**
 * @author suo
 * @date 2018/10/22
 * @desc: ImageGridAdapter
 */
class ImageGridAdapter(private val mContext: Context, showCamera: Boolean, private var mItemSize: Int) : BaseAdapter() {

    private val mInflater: LayoutInflater by lazy {
            mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }
    private var showCamera = true
    private var showSelectIndicator = true

    private var mImages: MutableList<Image>? = ArrayList()
    private val mSelectedImages = ArrayList<Image>()
    private var mItemLayoutParams: AbsListView.LayoutParams? = null

    var isShowCamera: Boolean
        get() = showCamera
        set(b) {
            if (showCamera == b) return

            showCamera = b
            notifyDataSetChanged()
        }

    init {
        this.showCamera = showCamera
        mItemLayoutParams = AbsListView.LayoutParams(mItemSize, mItemSize)
    }

    /**
     * 显示选择指示器
     * @param b
     */
    fun showSelectIndicator(b: Boolean) {
        showSelectIndicator = b
    }

    /**
     * 选择某个图片，改变选择状态
     * @param image
     */
    fun select(image: Image) {
        if (mSelectedImages.contains(image)) {
            mSelectedImages.remove(image)
        } else {
            mSelectedImages.add(image)
        }
        notifyDataSetChanged()
    }

    /**
     * 通过图片路径设置默认选择
     * @param resultList
     */
    fun setDefaultSelected(resultList: ArrayList<String>) {
        mSelectedImages.clear()
        for (path in resultList) {
            val image = getImageByPath(path)
            if (image != null) {
                mSelectedImages.add(image)
            }
        }
        notifyDataSetChanged()
    }

    private fun getImageByPath(path: String): Image? {
        if (mImages != null && mImages!!.size > 0) {
            for (image in mImages!!) {
                if (image.path.equals(path, ignoreCase = true)) {
                    return image
                }
            }
        }
        return null
    }

    /**
     * 设置数据集
     * @param images
     */
    fun setData(images: MutableList<Image>?) {
        mSelectedImages.clear()

        if (images != null && images.size > 0) {
            mImages = images
        } else {
            mImages!!.clear()
        }
        notifyDataSetChanged()
    }

    /**
     * 重置每个Column的Size
     * @param columnWidth
     */
    fun setItemSize(columnWidth: Int) {

        if (mItemSize == columnWidth) {
            return
        }

        mItemSize = columnWidth

        mItemLayoutParams = AbsListView.LayoutParams(mItemSize, mItemSize)

        notifyDataSetChanged()
    }

    override fun getViewTypeCount(): Int {
        return 2
    }

    override fun getItemViewType(position: Int): Int {
        return if (showCamera) {
            if (position == 0) TYPE_CAMERA else TYPE_NORMAL
        } else TYPE_NORMAL
    }

    override fun getCount(): Int {
        return if (showCamera) mImages!!.size + 1 else mImages!!.size
    }

    override fun getItem(i: Int): Image? {
        return if (showCamera) {
            if (i == 0) {
                null
            } else mImages!![i - 1]
        } else {
            mImages!![i]
        }
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        var view = view

        val type = getItemViewType(i)
        if (type == TYPE_CAMERA) {
            view = mInflater.inflate(R.layout.item_camera, viewGroup, false)
            view.tag = null
        } else if (type == TYPE_NORMAL) {
            var holde: ViewHolde?
            if (view == null) {
                view = mInflater.inflate(R.layout.item_select_image, viewGroup, false)
                holde = ViewHolde(view!!)
            } else {
                holde = view.tag as ViewHolde?
                if (holde == null) {
                    view = mInflater.inflate(R.layout.item_select_image, viewGroup, false)
                    holde = ViewHolde(view!!)
                }
            }
            holde.bindData(getItem(i))
        }

        /** Fixed View Size  */
        val lp = view!!.layoutParams as AbsListView.LayoutParams
        if (lp.height != mItemSize) {
            view.layoutParams = mItemLayoutParams
        }

        return view
    }

    internal inner class ViewHolde(view: View) {
        var image: ImageView
        var indicator: ImageView
        var mask: View

        init {
            image = view.findViewById<View>(R.id.image) as ImageView
            indicator = view.findViewById<View>(R.id.checkmark) as ImageView
            mask = view.findViewById(R.id.mask)
            view.tag = this
        }

        fun bindData(data: Image?) {
            if (data == null) return
            // 处理单选和多选状态
            if (showSelectIndicator) {
                indicator.visibility = View.VISIBLE
                if (mSelectedImages.contains(data)) {
                    // 设置选中状态
                    indicator.setImageResource(R.mipmap.btn_selected)
                    mask.visibility = View.VISIBLE
                } else {
                    // 未选择
                    indicator.setImageResource(R.mipmap.btn_unselected)
                    mask.visibility = View.GONE
                }
            } else {
                indicator.visibility = View.GONE
            }
            val imageFile = File(data.path)

            if (mItemSize > 0) {
                // 显示图片
                Glide.with(mContext)
                        .load(imageFile)
                        .apply(RequestOptions().placeholder(R.mipmap.default_error).error(R.mipmap.default_error).override(mItemSize, mItemSize)
                                .centerCrop())
                        .into(image)
            }
        }
    }

    companion object {

        private val TYPE_CAMERA = 0
        private val TYPE_NORMAL = 1
    }

}
