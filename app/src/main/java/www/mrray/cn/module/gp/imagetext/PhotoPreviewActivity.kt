package www.mrray.cn.module.gp.imagetext

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_image_preview.*
import www.mrray.cn.R
import java.util.*

/**
 * @author suo
 * @date 2018/10/22
 * @desc:
 */
class PhotoPreviewActivity : AppCompatActivity(), PhotoPagerAdapter.PhotoViewClickListener {

    private var paths: ArrayList<String>? = null
    private var mPagerAdapter: PhotoPagerAdapter? = null
    private var currentItem = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_image_preview)

        initViews()

        paths = ArrayList()
        val pathArr = intent.getStringArrayListExtra(EXTRA_PHOTOS)
        if (pathArr != null) {
            paths!!.addAll(pathArr)
        }
        paths!!.removeAt(pathArr!!.size - 1)
        currentItem = intent.getIntExtra(EXTRA_CURRENT_ITEM, 0)

        mPagerAdapter = PhotoPagerAdapter(this, paths)
        mPagerAdapter!!.setPhotoViewClickListener(this)
        mViewPager!!.adapter = mPagerAdapter
        mViewPager!!.currentItem = currentItem
        mViewPager!!.offscreenPageLimit = 5

        mViewPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                updateActionBarTitle()
            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
        updateActionBarTitle()
    }

    private fun initViews() {
        val mToolbar = findViewById<View>(R.id.pickerToolbar) as Toolbar
        setSupportActionBar(mToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun OnPhotoTapListener(view: View, v: Float, v1: Float) {
        onBackPressed()
    }

    fun updateActionBarTitle() {
        supportActionBar!!.title = getString(R.string.image_index, mViewPager!!.currentItem + 1, paths!!.size)
    }

    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra(EXTRA_RESULT, paths)
        setResult(Activity.RESULT_OK, intent)
        finish()
        super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_preview, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        // 删除当前照片
        if (item.itemId == R.id.action_discard) {
            val index = mViewPager!!.currentItem
            val deletedPath = paths!![index]
            val snackbar = Snackbar.make(window.decorView.findViewById(android.R.id.content), R.string.deleted_a_photo,
                    Snackbar.LENGTH_LONG)
            if (paths!!.size <= 1) {
                // 最后一张照片弹出删除提示
                // show confirm dialog
                AlertDialog.Builder(this)
                        .setTitle(R.string.confirm_to_delete)
                        .setPositiveButton(R.string.yes) { dialogInterface, i ->
                            dialogInterface.dismiss()
                            paths!!.removeAt(index)
                            onBackPressed()
                        }
                        .setNegativeButton(R.string.cancel) { dialogInterface, i -> dialogInterface.dismiss() }
                        .show()
            } else {
                snackbar.show()
                paths!!.removeAt(index)
                mPagerAdapter!!.notifyDataSetChanged()
            }

            snackbar.setAction(R.string.undo) {
                if (paths!!.size > 0) {
                    paths!!.add(index, deletedPath)
                } else {
                    paths!!.add(deletedPath)
                }
                mPagerAdapter!!.notifyDataSetChanged()
                mViewPager!!.setCurrentItem(index, true)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        val EXTRA_PHOTOS = "extra_photos"
        val EXTRA_CURRENT_ITEM = "extra_current_item"

        /** 选择结果，返回为 ArrayList&lt;String&gt; 图片路径集合   */
        val EXTRA_RESULT = "preview_result"

        /** 预览请求状态码  */
        val REQUEST_PREVIEW = 99
    }
}
