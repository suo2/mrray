package www.mrray.cn.module.gp.activity

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.LinearLayoutManager
import android.util.TypedValue
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_gp_manager.*
import kotlinx.android.synthetic.main.layout_gp_manager_activity.*
import kotlinx.android.synthetic.main.layout_gp_manager_four_btn.*
import kotlinx.android.synthetic.main.layout_gp_manager_info.*
import kotlinx.android.synthetic.main.layout_gp_manager_top.*
import org.jetbrains.anko.intentFor
import www.mrray.cn.BuildConfig
import www.mrray.cn.R
import www.mrray.cn.base.BaseViewModelActivity
import www.mrray.cn.model.gp.GpManagerDetailModel
import www.mrray.cn.module.gp.adapter.GpManagerDetailAdapter
import www.mrray.cn.module.web.JSBridgeWebViewActivity
import www.mrray.cn.repository.gp.GpDetailRepository
import www.mrray.cn.viewmodel.gp.GpDetailViewModel

open class GpManagerDetailActivity : BaseViewModelActivity<GpDetailViewModel, GpDetailRepository>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_gp_manager
    }

    companion object {
        const val EXTRA_FOUND_ID = "foundId"
        const val EXTRA_FOUND_NAME = "found_name"
    }

    private var mFundId = 0

    private var isNeedAdapta = true

    override fun init(savedInstanceState: Bundle?) {
        super.init(savedInstanceState)
        initRecyclerView()
        title_bar.setTitleContentText(intent.getStringExtra(EXTRA_FOUND_NAME))
        title_bar.setLeftImgListener { finish() }
        autoMatchFont(title_bar.mTitleText)
        initListener()
        //由于进入的时候会滑动到底部
        gp_manager_detail_scroll.post {
            kotlin.run {
                gp_manager_detail_scroll.fullScroll(NestedScrollView.FOCUS_UP)
            }
        }

        getDataInfo()
    }


    private fun getDataInfo() {
        mFundId = intent.getIntExtra(EXTRA_FOUND_ID, 0)
        mViewModel.getGpDetailData(mFundId)
    }

    private fun initListener() {
        gp_manager_detail_raise_btn.setOnClickListener {
            startActivity(intentFor<JSBridgeWebViewActivity>(JSBridgeWebViewActivity.EXTRA_NAME to "资金募集",
                    JSBridgeWebViewActivity.EXTRA_URL to BuildConfig.H5_HOST + "/raise?fundId=" + mFundId))
        }

        gp_manager_detail_cast_btn.setOnClickListener {
            startActivity(intentFor<JSBridgeWebViewActivity>(JSBridgeWebViewActivity.EXTRA_NAME to "基金投资",
                    JSBridgeWebViewActivity.EXTRA_URL to BuildConfig.H5_HOST + "/cast?fundId="+mFundId))
        }

        gp_manager_detail_manager_btn.setOnClickListener {
            startActivity(intentFor<JSBridgeWebViewActivity>(JSBridgeWebViewActivity.EXTRA_NAME to "基金管理",
                    JSBridgeWebViewActivity.EXTRA_URL to BuildConfig.H5_HOST +"/manager?fundId="+mFundId))
        }

        gp_manager_detail_exit_btn.setOnClickListener {
            startActivity(intentFor<JSBridgeWebViewActivity>(JSBridgeWebViewActivity.EXTRA_NAME to "基金退出",
                    JSBridgeWebViewActivity.EXTRA_URL to BuildConfig.H5_HOST +"/exit?fundId="+mFundId))
        }
    }

    private fun initRecyclerView() {
        val datalist: ArrayList<GpManagerDetailModel> = ArrayList()
        datalist.add(GpManagerDetailModel(1, "2018.09.09", "完成中基协备案，备案号：SD2345。托管银行：招商银行股份有限公司。"))
        datalist.add(GpManagerDetailModel(1, "2018.08.11", "签订协议，与投资人签订保密协议。"))
        datalist.add(GpManagerDetailModel(1, "2018.08.11", "签订协议，与投资人签订保密协议。"))
        datalist.add(GpManagerDetailModel(1, "2018.07.01", "签订合同，与投资人签订正式协议，出售5000万基金份额。"))
        datalist.add(GpManagerDetailModel(1, "2018.06.11", "完成中基协备案，备案号：SD2345。托管银行：招商银行股份有限公司。"))

        gp_manager_recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        gp_manager_recycler_view.adapter = GpManagerDetailAdapter(datalist)
    }

    @SuppressLint("SetTextI18n")
    override fun setUpViewModel() {
        super.setUpViewModel()
        mViewModel.mGpDetailHeadInfoModel.observe(this, Observer {
            gp_detail_amount.text = it?.amount + "万"
            gp_manager_annualized_income_txt.text = it?.irr
            gp_manager_info_casted_money_count_txt.text = it?.ytAmount + "万"
            gp_manager_info_cast_money_count_txt.text = it?.ktAmount + "万"
            gp_manager_info_project_num_count_txt.text = it?.cpCount + "个"
            gp_manager_return_count_txt.text = it?.returnMultiple+ "倍"
        })
    }

    protected fun autoMatchFont(view: TextView) {
        view.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            val vWidth = v.width.toFloat()
            //LogUtil.d("vWidth=" + vWidth);
            val paint = view.paint
            val text = view.text.toString()
            val textLen = paint.measureText(text)
            //LogUtil.d("textLen=" + textLen);
            val oldSize = view.textSize
            //LogUtil.d("oldSize=" + oldSize);
            if (textLen != vWidth) {
                val size = vWidth * oldSize / textLen
                //LogUtil.d("size=" + size);
                view.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
            }
        }
    }

}