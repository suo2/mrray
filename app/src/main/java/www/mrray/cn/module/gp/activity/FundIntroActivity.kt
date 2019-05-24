package www.mrray.cn.module.gp.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_fund_intro.*
import org.json.JSONArray
import www.mrray.cn.R
import www.mrray.cn.base.BaseActivity
import www.mrray.cn.module.gp.adapter.FundintroAdapter
import www.mrray.cn.module.gp.imagetext.PhotoPickerActivity
import www.mrray.cn.module.gp.imagetext.PhotoPreviewActivity
import www.mrray.cn.module.gp.imagetext.SelectModel
import www.mrray.cn.module.gp.imagetext.intent.PhotoPickerIntent
import www.mrray.cn.module.gp.imagetext.intent.PhotoPreviewIntent
import java.util.*

/**
 *@author suo
 *@date 2018/10/22
 *@desc: 基金介绍
 */
class FundIntroActivity : BaseActivity() {
    private val imagePaths = ArrayList<String>()

    private var gridAdapter: FundintroAdapter? = null
    private val TAG = FundIntroActivity::class.java.simpleName

    override fun getLayoutId(): Int {
        return R.layout.activity_fund_intro
    }

    override fun init(savedInstanceState: Bundle?) {
        super.init(savedInstanceState)

        var cols = resources.displayMetrics.widthPixels / resources.displayMetrics.densityDpi
        cols = if (cols < 3) 3 else cols
        gridView!!.numColumns = cols
        gridView!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val imgs = parent.getItemAtPosition(position) as String
            if ("paizhao" == imgs) {
                val intent = PhotoPickerIntent(this@FundIntroActivity)
                intent.setSelectModel(SelectModel.MULTI)
                intent.setShowCarema(true) // 是否显示拍照
                intent.setMaxTotal(6) // 最多选择照片数量，默认为6
                intent.setSelectedPaths(imagePaths) // 已选中的照片地址， 用于回显选中状态
                startActivityForResult(intent, REQUEST_CAMERA_CODE)
            } else {
                Toast.makeText(this@FundIntroActivity, "1$position", Toast.LENGTH_SHORT).show()
                val intent = PhotoPreviewIntent(this@FundIntroActivity)
                intent.setCurrentItem(position)
                intent.setPhotoPaths(imagePaths)
                startActivityForResult(intent, REQUEST_PREVIEW_CODE)
            }
        }
        imagePaths.add("paizhao")
        gridAdapter = FundintroAdapter(this, imagePaths)
        gridView!!.adapter = gridAdapter
    }


    override fun onResume() {
        super.onResume()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                // 选择照片
                REQUEST_CAMERA_CODE -> {
                    val list = data!!.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT)
                    Log.d(TAG, "数量：" + list.size)
                    loadAdpater(list)
                }
                // 预览
                REQUEST_PREVIEW_CODE -> {
                    val ListExtra = data!!.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT)
                    loadAdpater(ListExtra)
                }
            }
        }
    }

    private fun loadAdpater(paths: ArrayList<String>) {
        if (imagePaths != null && imagePaths.size > 0) {
            imagePaths.clear()
        }
        if (paths.contains("paizhao")) {
            paths.remove("paizhao")
        }
        paths.add("paizhao")
        imagePaths.addAll(paths)
        gridAdapter = FundintroAdapter(this, imagePaths)
        gridView!!.adapter = gridAdapter
        try {
            val obj = JSONArray(imagePaths)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    companion object {

        private val REQUEST_CAMERA_CODE = 10
        private val REQUEST_PREVIEW_CODE = 20
    }

}
