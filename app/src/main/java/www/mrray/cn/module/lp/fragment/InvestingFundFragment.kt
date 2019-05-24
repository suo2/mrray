package www.mrray.cn.module.lp.fragment

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import kotlinx.android.synthetic.main.fragment_investing_fund.*
import www.mrray.cn.R
import www.mrray.cn.base.BaseViewModelFragment
import www.mrray.cn.customexception.HttpException
import www.mrray.cn.http.requestbody.PageRequestBody
import www.mrray.cn.model.PageBaseModel
import www.mrray.cn.model.lp.InvestingFundModel
import www.mrray.cn.module.gp.SpaceItemDecoration
import www.mrray.cn.module.lp.adapter.InvestingFundAdapter
import www.mrray.cn.repository.lp.LpHomeRepository
import www.mrray.cn.viewmodel.lp.LpHomeViewModel

/**
 *@author suo
 *@date 2018/10/29
 *@desc: 投资中基金
 */
class InvestingFundFragment : BaseViewModelFragment<LpHomeViewModel, LpHomeRepository>() {

    private  var projectList = ArrayList<InvestingFundModel>()

    private lateinit var mInvestingFundAdapter: InvestingFundAdapter

    private lateinit var mPageRequestBody: PageRequestBody

    private var mPageBaseModel : PageBaseModel<InvestingFundModel>? = null


    var isLoadMore : Boolean = false

    companion object {
        fun newInstance() = InvestingFundFragment()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_investing_fund
    }

    override fun init(view: View, savedInstanceState: Bundle?) {
        super.init(view, savedInstanceState)
        initView()
        requestData()
    }

    /**
     * 初始化
     */
    private fun initView(){

        recyclerView.layoutManager = object : LinearLayoutManager(context) {}
        recyclerView.addItemDecoration(SpaceItemDecoration(20))

        filter.setOnTouchListener { v, event -> false
            filter.setFilterListener(event){
                mPageRequestBody.direction = it.sort
                mPageRequestBody.property = it.position.toString()
                mPageRequestBody.page = 1
                mViewModel.getFundInvesting(mPageRequestBody.addValue("roleId",mDataCatch.getLoginModel()?.roelId.toString()))
            }
        }

        refreshLayout.setRefreshHeader(ClassicsHeader(activity))
        refreshLayout.setRefreshFooter(ClassicsFooter(activity!!))
        refreshLayout.setOnRefreshListener {
            refreshLayout.setNoMoreData(false)
            mPageRequestBody.page = 1
            mViewModel.getFundInvesting(mPageRequestBody.addValue("roleId",mDataCatch.getLoginModel()?.roelId.toString()))
            isLoadMore = false
        }
        refreshLayout.setOnLoadMoreListener {
            if(mPageRequestBody.page== mPageBaseModel!!.totalPage){
                refreshLayout.finishLoadMoreWithNoMoreData()
                return@setOnLoadMoreListener
            }
            mPageRequestBody.page++
            mViewModel.getFundInvesting(mPageRequestBody.addValue("roleId",mDataCatch.getLoginModel()?.roelId.toString()))
            isLoadMore = true
        }
    }

    private fun requestData(){
        mPageRequestBody= PageRequestBody("","0",1)
        mViewModel.getFundInvesting(mPageRequestBody.addValue("roleId",mDataCatch.getLoginModel()?.roelId.toString()))
    }

    override fun setUpViewModel() {
        super.setUpViewModel()
        mViewModel.mInvestingFundModel.observe(this, Observer {
            mPageBaseModel = it as PageBaseModel<InvestingFundModel>
            if(mPageBaseModel!!.content != null){
                projectList = mPageBaseModel!!.content as ArrayList
            }
            if(isLoadMore){
                mInvestingFundAdapter.addMoreData(projectList)
                refreshLayout.finishLoadMore()
            }else{
                mInvestingFundAdapter = InvestingFundAdapter(activity!!, projectList)
                recyclerView.adapter = mInvestingFundAdapter
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