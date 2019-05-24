package www.mrray.cn.module.lp.fragment

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import kotlinx.android.synthetic.main.fragment_invested_project.*
import www.mrray.cn.R
import www.mrray.cn.base.BaseViewModelFragment
import www.mrray.cn.customexception.HttpException
import www.mrray.cn.http.requestbody.PageRequestBody
import www.mrray.cn.model.PageBaseModel
import www.mrray.cn.model.lp.InvestedProjectModel
import www.mrray.cn.module.gp.SpaceItemDecoration
import www.mrray.cn.module.lp.adapter.InvestedProjectAdapter
import www.mrray.cn.repository.lp.LpHomeRepository
import www.mrray.cn.viewmodel.lp.LpHomeViewModel

/**
 *@author suo
 *@date 2018/10/29
 *@desc: 已投项目
 */
class InvestedProjectFragment : BaseViewModelFragment<LpHomeViewModel, LpHomeRepository>() {

    private  var projectList = ArrayList<InvestedProjectModel>()

    private lateinit var mInvestedProjectAdapter: InvestedProjectAdapter

    private lateinit var mPageRequestBody: PageRequestBody

    private var mPageBaseModel : PageBaseModel<InvestedProjectModel>? = null


    var isLoadMore : Boolean = false

    companion object {
        fun newInstance() = InvestedProjectFragment()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_invested_project
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
                mViewModel.getProjectInvested(mPageRequestBody.addValue("roleId",mDataCatch.getLoginModel()?.roelId.toString()))
            }
        }

        refreshLayout.setRefreshHeader(ClassicsHeader(activity))
        refreshLayout.setRefreshFooter(ClassicsFooter(activity!!))
        refreshLayout.setOnRefreshListener {
            refreshLayout.setNoMoreData(false)
            mPageRequestBody.page = 1
            mViewModel.getProjectInvested(mPageRequestBody.addValue("roleId",mDataCatch.getLoginModel()?.roelId.toString()))
            isLoadMore = false
        }
        refreshLayout.setOnLoadMoreListener {
            if(mPageRequestBody.page== mPageBaseModel!!.totalPage){
                refreshLayout.finishLoadMoreWithNoMoreData()
                return@setOnLoadMoreListener
            }
            mPageRequestBody.page++
            mViewModel.getProjectInvested(mPageRequestBody.addValue("roleId",mDataCatch.getLoginModel()?.roelId.toString()))
            isLoadMore = true
        }
    }

    private fun requestData(){
        mPageRequestBody= PageRequestBody("","0",1)
        mViewModel.getProjectInvested(mPageRequestBody.addValue("roleId",mDataCatch.getLoginModel()?.roelId.toString()))
    }

    override fun setUpViewModel() {
        super.setUpViewModel()
        mViewModel.mInvestedProjectModel.observe(this, Observer {
            mPageBaseModel = it as PageBaseModel<InvestedProjectModel>
            if(mPageBaseModel!!.content != null){
                projectList = mPageBaseModel!!.content as ArrayList
            }
            if(isLoadMore){
                mInvestedProjectAdapter.addMoreData(projectList)
                refreshLayout.finishLoadMore()
            }else{
                mInvestedProjectAdapter = InvestedProjectAdapter(activity!!, projectList)
                recyclerView.adapter = mInvestedProjectAdapter
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