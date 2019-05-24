package www.mrray.cn.module.gp

import android.arch.lifecycle.Observer
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.TextView
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.view.OptionsPickerView
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import kotlinx.android.synthetic.main.fragment_lp_manage_list.*
import www.mrray.cn.R
import www.mrray.cn.base.BaseViewModelFragment
import www.mrray.cn.customexception.HttpException
import www.mrray.cn.http.requestbody.PageRequestBody
import www.mrray.cn.model.PageBaseModel
import www.mrray.cn.model.gp.FundListModel
import www.mrray.cn.model.gp.LpManageModel
import www.mrray.cn.module.gp.adapter.LpManageAdapter
import www.mrray.cn.repository.gp.GpHomeRepository
import www.mrray.cn.viewmodel.gp.GpHomeViewModel

/**
 *@author suo
 *@date 2018/10/17
 *@desc: 基金管理列表
 */
class LpManageListFragment : BaseViewModelFragment<GpHomeViewModel, GpHomeRepository>() {

    private lateinit var fundList: ArrayList<FundListModel>
    private lateinit var pvOptions : OptionsPickerView<Any>

    private lateinit var list: ArrayList<LpManageModel>

    private lateinit var mLpManageAdapter: LpManageAdapter

    private lateinit var mPageRequestBody: PageRequestBody

    private var mPageBaseModel : PageBaseModel<LpManageModel>? = null


    var isLoadMore : Boolean = false

    private var fundPosition : Int = 0

    private var descDrawable : Drawable? = null
    private var ascDrawable : Drawable? = null
    private val DESC : String = "DESC" //降序
    private val ASC :String = "ASC" //升序
    private var contributionAmountSort :String = DESC
    var fundId : String = "0"

    companion object {
        fun newInstance() = LpManageListFragment()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_lp_manage_list
    }

    override fun init(view: View, savedInstanceState: Bundle?) {
        super.init(view, savedInstanceState)
        initView()
        initPicker(view)
        requestData()
    }

    /**
     * 初始化数据
     */

    private fun initView(){
        list = ArrayList()

        recyclerView.layoutManager = object : LinearLayoutManager(context) {
        }

        mLpManageAdapter = LpManageAdapter(activity!!, list)
        recyclerView.addItemDecoration(SpaceItemDecoration(20))
        recyclerView.adapter = mLpManageAdapter

        fundSelect.setOnClickListener{
            pvOptions.show(fundSelect,true)
        }
        descDrawable = ContextCompat.getDrawable(activity!!,R.mipmap.sort_desc)
        descDrawable!!.setBounds(0, 0, descDrawable!!.minimumWidth, descDrawable!!.minimumHeight)
        ascDrawable = ContextCompat.getDrawable(activity!!,R.mipmap.sort_asc)
        ascDrawable!!.setBounds(0, 0, descDrawable!!.minimumWidth, descDrawable!!.minimumHeight)

        contributionAmount.setOnClickListener{
            contributionAmountSort = setTabStatus(contributionAmount, contributionAmountSort)
            mPageRequestBody.direction = contributionAmountSort
            mPageRequestBody.page = 1
            mViewModel.getLpListByPage(mPageRequestBody.addValue("roleId",mDataCatch.getLoginModel()?.roelId.toString()))
        }
    }

    /**
     * 设置点击状态
     */
    private fun setTabStatus(tv : TextView, sort : String) : String{
        tv.setTextColor(ContextCompat.getColor(activity!!,R.color.tab_sort_select))
        return if(sort == DESC) {
            tv.setCompoundDrawables(null, null, ascDrawable, null)
            ASC
        }else{
            tv.setCompoundDrawables(null, null, descDrawable, null)
            DESC
        }
    }

    /**
     * 初始化选择器
     */
    private fun initPicker(view: View){
        pvOptions = OptionsPickerBuilder(activity, OnOptionsSelectListener { options1, option2, options3, v ->
            //返回的分别是三个级别的选中位置
        }).setOptionsSelectChangeListener { options1, options2, options3 ->
            fundPosition = options1
        }.setLayoutRes(R.layout.view_manage_fund_select) { v ->
            val tvSubmit = v.findViewById(R.id.tv_commit) as TextView
            val tvCancel = v.findViewById(R.id.tv_cancel) as TextView
            tvSubmit.setOnClickListener {
                fundSelect.text=fundList[fundPosition].name
                mPageRequestBody.property = fundPosition.toString()
                mPageRequestBody.page = 1
                fundId = fundList[fundPosition].id.toString()
                var map: HashMap<String,String> = mPageRequestBody.addValue("roleId",mDataCatch.getLoginModel()?.roelId.toString())
                map["fundId"] = fundId
                mViewModel.getLpListByPage(map)
                pvOptions.dismiss()
            }
            tvCancel.setOnClickListener { pvOptions.dismiss() }
        }
                .setTitleText("选择基金")
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setSelectOptions(fundPosition)//默认选中项
                .setOutSideCancelable(false)
                .setBgColor(ContextCompat.getColor(activity!!,R.color.light_gray))
                .setDecorView(view.findViewById(R.id.parent))
                .setTitleBgColor(ContextCompat.getColor(activity!!,R.color.light_gray))
                .isDialog(false)
                .build()

        refreshLayout.setRefreshHeader(ClassicsHeader(activity))
        refreshLayout.setRefreshFooter(ClassicsFooter(activity!!))

        
        refreshLayout.setOnRefreshListener {
            refreshLayout.setNoMoreData(false)
            mPageRequestBody.page = 1
            var map: HashMap<String,String> = mPageRequestBody.addValue("roleId",mDataCatch.getLoginModel()?.roelId.toString())
            map["fundId"] = fundId
            mViewModel.getLpListByPage(map)
            isLoadMore = false
        }

        refreshLayout.setOnLoadMoreListener {
            if(mPageRequestBody.page== mPageBaseModel!!.totalPage){
                refreshLayout.finishLoadMoreWithNoMoreData()
                return@setOnLoadMoreListener
            }
            var map: HashMap<String,String> = mPageRequestBody.addValue("roleId",mDataCatch.getLoginModel()?.roelId.toString())
            map["fundId"] = fundId
            mPageRequestBody.page++
            mViewModel.getLpListByPage(map)
            isLoadMore = true
        }
    }

    fun requestData(){
        mPageRequestBody= PageRequestBody("","0",1)
        mViewModel.getFundList(1)
        var map: HashMap<String,String> = mPageRequestBody.addValue("roleId",mDataCatch.getLoginModel()?.roelId.toString())
        map["fundId"] = fundId
        mViewModel.getLpListByPage(map)
    }
    var data =  ArrayList<String>()

    override fun setUpViewModel() {
        super.setUpViewModel()
        mViewModel.mFundListModel.observe(this, Observer {
            fundList = it as ArrayList<FundListModel>
            fundList.add(0,FundListModel(0,"全部基金"))
            for(i in fundList .indices){
                data.add(fundList[i].name)
            }
            pvOptions.setPicker(data as List<Any>?)
        })

        mViewModel.mLpManageModel.observe(this, Observer {
            mPageBaseModel = it as PageBaseModel<LpManageModel>
            if(mPageBaseModel!!.content != null){
                list = mPageBaseModel!!.content as ArrayList
            }

            if(isLoadMore){
                mLpManageAdapter.addMoreData(list)
                refreshLayout.finishLoadMore()
            }else{
                mLpManageAdapter = LpManageAdapter(activity!!, list)
                recyclerView.adapter = mLpManageAdapter
                refreshLayout.finishRefresh()
            }
        })
    }
    override fun onNetError(it: HttpException?) {
        super.onNetError(it)
        refreshLayout.finishRefresh()
        refreshLayout.finishLoadMore()
    }
}