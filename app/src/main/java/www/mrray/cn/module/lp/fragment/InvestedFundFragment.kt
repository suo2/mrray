package www.mrray.cn.module.lp.fragment

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import kotlinx.android.synthetic.main.fragment_invested_fund.*
import www.mrray.cn.R
import www.mrray.cn.base.BaseViewModelFragment
import www.mrray.cn.customexception.HttpException
import www.mrray.cn.http.requestbody.PageRequestBody
import www.mrray.cn.model.PageBaseModel
import www.mrray.cn.model.lp.InvestedFundModel
import www.mrray.cn.module.gp.SpaceItemDecoration
import www.mrray.cn.module.lp.adapter.InvestedFundAdapter
import www.mrray.cn.repository.lp.LpHomeRepository
import www.mrray.cn.viewmodel.lp.LpHomeViewModel

/**
 *@author suo
 *@date 2018/10/29
 *@desc: 已投基金
 */
class InvestedFundFragment : BaseViewModelFragment<LpHomeViewModel, LpHomeRepository>() {

    private  var projectList = ArrayList<InvestedFundModel>()

    private lateinit var mInvestedFundAdapter: InvestedFundAdapter

    private lateinit var mPageRequestBody: PageRequestBody

    private var mPageBaseModel : PageBaseModel<InvestedFundModel>? = null


    var isLoadMore : Boolean = false

    companion object {
        fun newInstance() = InvestedFundFragment()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_invested_fund
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
                mViewModel.getFundInvested(mPageRequestBody.addValue("roleId",mDataCatch.getLoginModel()?.roelId.toString()))
            }
        }

        refreshLayout.setRefreshHeader(ClassicsHeader(activity))
        refreshLayout.setRefreshFooter(ClassicsFooter(activity!!))
        refreshLayout.setOnRefreshListener {
            refreshLayout.setNoMoreData(false)
            mPageRequestBody.page = 1
            mViewModel.getFundInvested(mPageRequestBody.addValue("roleId",mDataCatch.getLoginModel()?.roelId.toString()))
            isLoadMore = false
        }
        refreshLayout.setOnLoadMoreListener {
            if(mPageRequestBody.page== mPageBaseModel!!.totalPage){
                refreshLayout.finishLoadMoreWithNoMoreData()
                return@setOnLoadMoreListener
            }
            mPageRequestBody.page++
            mViewModel.getFundInvested(mPageRequestBody.addValue("roleId",mDataCatch.getLoginModel()?.roelId.toString()))
            isLoadMore = true
        }
    }

    private fun requestData(){
        mPageRequestBody= PageRequestBody("","0",1)
        mViewModel.getFundInvested(mPageRequestBody.addValue("roleId",mDataCatch.getLoginModel()?.roelId.toString()))
    }

    override fun setUpViewModel() {
        super.setUpViewModel()
        mViewModel.mInvestedFundModel.observe(this, Observer {
            mPageBaseModel = it as PageBaseModel<InvestedFundModel>
            if(mPageBaseModel!!.content != null){
                projectList = mPageBaseModel!!.content as ArrayList
            }

            if(isLoadMore){
                mInvestedFundAdapter.addMoreData(projectList)
                refreshLayout.finishLoadMore()
            }else{
                mInvestedFundAdapter = InvestedFundAdapter(activity!!, projectList)
                recyclerView.adapter = mInvestedFundAdapter
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