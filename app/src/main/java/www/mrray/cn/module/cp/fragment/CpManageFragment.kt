package www.mrray.cn.module.cp.fragment

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_cp_manage.*
import org.jetbrains.anko.support.v4.startActivity
import www.mrray.cn.BuildConfig
import www.mrray.cn.R
import www.mrray.cn.base.BaseViewModelFragment
import www.mrray.cn.customexception.HttpException
import www.mrray.cn.file.FileManagerActivity
import www.mrray.cn.model.cp.ProjectManageInfoModel
import www.mrray.cn.module.cp.BusinessSummaryActivity
import www.mrray.cn.module.cp.adapter.ProjectEventAdapter
import www.mrray.cn.module.cp.adapter.ProjectFinancingAdapter
import www.mrray.cn.module.gp.SpaceItemDecoration
import www.mrray.cn.module.web.JSBridgeWebViewActivity
import www.mrray.cn.repository.cp.CpHomeRepository
import www.mrray.cn.utils.MPChartHelper
import www.mrray.cn.utils.NumberFormatUtil
import www.mrray.cn.utils.Utils
import www.mrray.cn.viewmodel.cp.CpHomeViewModel
import java.util.*


/**
 *@author suo
 *@date 2018/10/16
 *@desc: CP 管理界面
 */
class CpManageFragment : BaseViewModelFragment<CpHomeViewModel, CpHomeRepository>() {
    private lateinit var projectList: ArrayList<ProjectManageInfoModel.Project>

    private lateinit var mProjectFinancingAdapter: ProjectFinancingAdapter

    private lateinit var projectEventList: ArrayList<ProjectManageInfoModel.ProjectEvent>

    private lateinit var mProjectEventAdapter: ProjectEventAdapter

    private var mProjectManageInfoModel: ProjectManageInfoModel? = null

    private lateinit var yAddValue: MutableList<Float>

    private lateinit var xAddValue: MutableList<String>

    private lateinit var yEarningsValue: MutableList<Float>

    private lateinit var xEarningsValue: MutableList<String>

    private lateinit var yOperateValue1: MutableList<Float>

    private lateinit var yOperateValue2: MutableList<Float>

    private lateinit var xOperateValue: MutableList<String>

    private var isEarningsFirst: Boolean = true

    private var isAddFirst: Boolean = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_cp_manage, container, false)

    companion object {
        fun newInstance() = CpManageFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        requestData()
    }

    private fun initView() {
        title.requestFocus()


        operatePreview.setOnClickListener {
//            startActivity<OperateReportActivity>()
        }

        cpOperate.setOnClickListener {
            //经营总结
            startActivity<BusinessSummaryActivity>(BusinessSummaryActivity.EXTRA_PROJECT_ID to mProjectManageInfoModel?.projectId)
        }

        cpFile.setOnClickListener {
            //文件管理
            startActivity<FileManagerActivity>(FileManagerActivity.FILE_TYPE_EXTRA to FileManagerActivity.FILE_CP,
                    FileManagerActivity.FILE_FOUND_ID to 0, FileManagerActivity.FILE_PROJECT_ID to mProjectManageInfoModel?.projectId)
        }

        cpInvestor.setOnClickListener {
            //投资人管理/projectDetail?projectId=
            startActivity<JSBridgeWebViewActivity>(JSBridgeWebViewActivity.EXTRA_NAME to "资金募集",
                    JSBridgeWebViewActivity.EXTRA_URL to BuildConfig.H5_HOST + "/projectDetail?projectId=" + mProjectManageInfoModel?.projectId)

        }

        projectList = ArrayList()
        projectRecyclerView.layoutManager = object : LinearLayoutManager(context) {
        }
        projectRecyclerView.addItemDecoration(SpaceItemDecoration(20))
        mProjectFinancingAdapter = ProjectFinancingAdapter(activity!!, projectList)
        projectRecyclerView.adapter = mProjectFinancingAdapter

        projectEventList = ArrayList()
        projectEventRecyclerView.layoutManager = object : LinearLayoutManager(context) {
        }
        projectEventRecyclerView.addItemDecoration(SpaceItemDecoration(20))
        mProjectEventAdapter = ProjectEventAdapter(activity!!, projectEventList)
        projectEventRecyclerView.adapter = mProjectEventAdapter
//
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimaryDark)
        mSwipeRefreshLayout.setOnRefreshListener {
            requestData()
        }
    }

    private fun requestData() {
        mViewModel.getProjectManageInfoByRoleId(mDataCatch.getLoginModel()?.roelId!!)
    }

    override fun setUpViewModel() {
        super.setUpViewModel()
        mViewModel.mProjectManageInfoModel?.observe(this, Observer {
            mSwipeRefreshLayout.isRefreshing = false
            mProjectManageInfoModel = it as ProjectManageInfoModel
            //介绍
            Glide.with(activity!!).load(BuildConfig.API_HOST + mProjectManageInfoModel?.logoPath)
                    .apply(RequestOptions.bitmapTransform(CircleCrop()).placeholder(R.mipmap.default_header)).into(logoPath)
            projectName.text = mProjectManageInfoModel?.projectName
            history_lp.text =  NumberFormatUtil.numberFormatToString(mProjectManageInfoModel?.hisLp,getString(R.string.unit_ming) , false)
            history_fund.text = NumberFormatUtil.numberFormatToString(mProjectManageInfoModel?.hisFund,getString(R.string.unit_zhi) , false)
            history_financing.text = NumberFormatUtil.numberFormatToString(mProjectManageInfoModel?.hisFinanceNum,getString(R.string.unit_ci) , false)
            history_amount.text = NumberFormatUtil.numberFormatToString(mProjectManageInfoModel?.hisFinanceAmount)

            //项目融资情况
            projectList = mProjectManageInfoModel?.projectList as ArrayList<ProjectManageInfoModel.Project>
            mProjectFinancingAdapter = ProjectFinancingAdapter(activity!!, projectList)
            projectRecyclerView.adapter = mProjectFinancingAdapter
            if(projectList.isEmpty()){
                projectRecyclerView.visibility = View.GONE
                projectNoData.visibility = View.VISIBLE
            }else{
                projectNoData.visibility = View.GONE
                projectRecyclerView.visibility = View.VISIBLE
            }

            yAddValue = ArrayList()
            xAddValue = ArrayList()
            yEarningsValue = ArrayList()
            xEarningsValue = ArrayList()
            yOperateValue1 = ArrayList()
            yOperateValue2 = ArrayList()
            xOperateValue = ArrayList()

            //盈利及估值情况
            setEarningsData()

            //经营报表
            setOperatingStatementData()

            //增元统计
            setAddAssetsData()

            //项目事件
            projectEventList = mProjectManageInfoModel?.projectEventList as ArrayList<ProjectManageInfoModel.ProjectEvent>
            mProjectEventAdapter = ProjectEventAdapter(activity!!, projectEventList)
            projectEventRecyclerView.adapter = mProjectEventAdapter
            if(projectEventList.isEmpty()){
                ll_projectEvent.visibility = View.GONE
                eventNoData.visibility = View.VISIBLE
            }else{
                eventNoData.visibility = View.GONE
                ll_projectEvent.visibility = View.VISIBLE
            }
        })
    }

    override fun onNetError(it: HttpException?) {
        super.onNetError(it)
        mSwipeRefreshLayout.isRefreshing = false
        eventNoData.visibility = View.VISIBLE
        projectNoData.visibility = View.VISIBLE
    }

    /**
     * 盈利及估值情况
     */
    private fun setEarningsData(){
        if (mProjectManageInfoModel != null && mProjectManageInfoModel?.earningsValuationList!=null) {
            for (i in mProjectManageInfoModel?.earningsValuationList!!.indices) {
                xEarningsValue.add(mProjectManageInfoModel?.earningsValuationList!![i].xtime)
                yEarningsValue.add(mProjectManageInfoModel?.earningsValuationList!![i].yvalue)
            }
            val maxLeft: Float = Collections.max(yEarningsValue)
            earningsUnit.text = Utils.getUnit(maxLeft)
            if (Utils.getRateUnit(maxLeft) != 1L) {
                yEarningsValue.clear()
                for (i: Int in xEarningsValue.indices) {
                    yEarningsValue.add(mProjectManageInfoModel?.earningsValuationList!![i].yvalue / Utils.getRateUnit(maxLeft))
                }
            }
            MPChartHelper.setLineChart(earningsLineChart, xEarningsValue, yEarningsValue, getString(R.string.earnings_valuation), isEarningsFirst)
            isEarningsFirst = false
        }
    }

    /**
     * 经营报表
     */
    private fun setOperatingStatementData(){
        if (mProjectManageInfoModel != null && mProjectManageInfoModel?.operatingStatementList!=null) {
            for (i in mProjectManageInfoModel?.operatingStatementList!!.indices) {
                xOperateValue.add(mProjectManageInfoModel?.operatingStatementList!![i].xtime)
                yOperateValue1.add(mProjectManageInfoModel?.operatingStatementList!![i].yvalue1)
                yOperateValue2.add(mProjectManageInfoModel?.operatingStatementList!![i].yvalue2)
            }
            val maxLeft1 : Float = Collections.max(yOperateValue1)
            val maxLeft2 : Float = Collections.max(yOperateValue2)
            val maxLeft = if(maxLeft1 > maxLeft2) maxLeft1 else maxLeft2
            barUnit.text = Utils.getUnit(maxLeft )
            if (Utils.getRateUnit(maxLeft) !=1L){
                yOperateValue1.clear()
                yOperateValue2.clear()
                for (i : Int in xOperateValue.indices){
                    yOperateValue1.add(mProjectManageInfoModel?.operatingStatementList!![i].yvalue1/ Utils.getRateUnit(maxLeft))
                    yOperateValue2.add(mProjectManageInfoModel?.operatingStatementList!![i].yvalue2/ Utils.getRateUnit(maxLeft))
                }
            }
            MPChartHelper.setTwoBarChart(barChart, xOperateValue, yOperateValue1, yOperateValue2,
                    getString(R.string.financial_statistics), getString(R.string.industry_statistics))
        }
    }

    /**
     * 增元统计FileManagerUtils.
     */
    private fun setAddAssetsData(){
        if (mProjectManageInfoModel != null && mProjectManageInfoModel?.addAssetsList!=null) {
            //增元统计
            for (i in mProjectManageInfoModel?.addAssetsList!!.indices) {
                xAddValue.add(mProjectManageInfoModel?.addAssetsList!![i].xtime)
                yAddValue.add(mProjectManageInfoModel?.addAssetsList!![i].yvalue)
            }
            val maxLeft: Float = Collections.max(yAddValue)
            addUnit.text = Utils.getUnit(maxLeft)
            if (Utils.getRateUnit(maxLeft) != 1L) {
                yAddValue.clear()
                for (i: Int in xAddValue.indices) {
                    yAddValue.add(mProjectManageInfoModel?.addAssetsList!![i].yvalue / Utils.getRateUnit(maxLeft))
                }
            }
            MPChartHelper.setLineChart(addLineChart, xAddValue, yAddValue, getString(R.string.addAssets_statistics), isAddFirst)
            isAddFirst = false
        }
    }


}