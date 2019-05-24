package www.mrray.cn.module.cp

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_business_summary.*
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import www.mrray.cn.R
import www.mrray.cn.base.BaseViewModelActivity
import www.mrray.cn.model.cp.BusinessSummaryModel
import www.mrray.cn.module.cp.adapter.BusinessSummaryAdapter
import www.mrray.cn.repository.cp.BusinessSummaryRepository
import www.mrray.cn.viewmodel.cp.BusinessSummaryViewModel

/**
 * 经营总结
 */
class BusinessSummaryActivity : BaseViewModelActivity<BusinessSummaryViewModel, BusinessSummaryRepository>() {
    private val mDataList = ArrayList<BusinessSummaryModel>()
    private val mAdapter = BusinessSummaryAdapter(mDataList)
    private lateinit var bottomSheetDialog: BottomSheetDialog

    companion object {
        val EXTRA_PROJECT_ID = "projectId"
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_business_summary
    }

    override fun init(savedInstanceState: Bundle?) {
        super.init(savedInstanceState)
        initData()
        initRecyclerView()
        initListener()
    }

    private fun initListener() {
        title_bar.setLeftImgListener { finish() }
        title_bar.setRightImgListener {
            initBottomSheetDialog()
        }
    }

    @SuppressLint("InflateParams")
    private fun getView(): View {
        val bottomSheetView = LayoutInflater.from(this).inflate(R.layout.dialog_business_bottom_sheet, null, false)
        bottomSheetView.find<ImageView>(R.id.close).setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        val mTitleEdit = bottomSheetView.find<EditText>(R.id.business_title_edit)
        val mContentEdit = bottomSheetView.find<EditText>(R.id.business_content_edit)
        bottomSheetView.find<Button>(R.id.business_submit).setOnClickListener {
            if (mTitleEdit.text.toString().trim().isEmpty() || mContentEdit.text.toString().trim().isEmpty()) {
                toast("请输入内容")
            } else {
                mViewModel.addBusinessSummary(mContentEdit.text.toString().trim(), 36, mTitleEdit.text.toString().trim())
            }

        }
        return bottomSheetView
    }

    private fun initBottomSheetDialog() {
        bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setCancelable(false)
        bottomSheetDialog.setContentView(getView())
        bottomSheetDialog.show()
    }

    private fun initData() {
        mViewModel.getBusinessSummary(intent.getIntExtra(EXTRA_PROJECT_ID, 0))
    }

    private fun initRecyclerView() {
        business_recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        business_recycler_view.adapter = mAdapter
    }

    override fun setUpViewModel() {
        super.setUpViewModel()
        mViewModel.mBusinessListModel.observe(this, Observer {
            mDataList.addAll(it!!)
        })

        mViewModel.mAddBusinessModel.observe(this, Observer {
            bottomSheetDialog.dismiss()
            mViewModel.getBusinessSummary(intent.getIntExtra(EXTRA_PROJECT_ID, 0))
        })
    }
}