package www.mrray.cn.module.gp

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import kotlinx.android.synthetic.main.fragment_funds_manage.*
import www.mrray.cn.R
import www.mrray.cn.base.BaseViewModelFragment
import www.mrray.cn.customexception.HttpException
import www.mrray.cn.http.requestbody.PageRequestBody
import www.mrray.cn.model.PageBaseModel
import www.mrray.cn.model.gp.FundsManageModel
import www.mrray.cn.module.gp.adapter.FundsManageAdapter
import www.mrray.cn.repository.gp.GpHomeRepository
import www.mrray.cn.viewmodel.gp.GpHomeViewModel

/**
 *@author suo
 *@date 2018/10/17
 *@desc: 基金管理列表
 */
class FundsManageFragment : BaseViewModelFragment<GpHomeViewModel, GpHomeRepository>() {


    private lateinit var list: ArrayList<FundsManageModel>

    private lateinit var mFundsManageAdapter: FundsManageAdapter

    private lateinit var mPageRequestBody: PageRequestBody

    private var mPageBaseModel : PageBaseModel<FundsManageModel>? = null

    var isLoadMore : Boolean = false

    companion object {
        fun newInstance() = FundsManageFragment()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_funds_manage
    }

    override fun init(view: View, savedInstanceState: Bundle?) {
        super.init(view, savedInstanceState)
        initView()
        requestData()
    }

    /**
     * 初始化数据
     */
    private fun initView(){
        list = ArrayList()
        recyclerView.layoutManager = object : LinearLayoutManager(context) {
        }
        mFundsManageAdapter = FundsManageAdapter(activity!!, list)
        recyclerView.addItemDecoration(SpaceItemDecoration(20))
        recyclerView.adapter = mFundsManageAdapter

        filter.setOnTouchListener { v, event -> false
            filter.setFilterListener(event){
                mPageRequestBody.direction = it.sort
                mPageRequestBody.property = it.position.toString()
                mPageRequestBody.page = 1
                mViewModel.getFundByRoleId(mPageRequestBody.addValue("roleId",mDataCatch.getLoginModel()?.roelId.toString()))
            }
        }

        refreshLayout.setRefreshHeader(ClassicsHeader(activity))
        refreshLayout.setRefreshFooter(ClassicsFooter(activity!!))
        refreshLayout.setOnRefreshListener {
            refreshLayout.setNoMoreData(false)
            mPageRequestBody.page = 1
            mViewModel.getFundByRoleId(mPageRequestBody.addValue("roleId",mDataCatch.getLoginModel()?.roelId.toString()))
            isLoadMore = false
        }
        refreshLayout.setOnLoadMoreListener {
            if(mPageRequestBody.page== mPageBaseModel!!.totalPage){
                refreshLayout.finishLoadMoreWithNoMoreData()
                return@setOnLoadMoreListener
            }
            mPageRequestBody.page++
            mViewModel.getFundByRoleId(mPageRequestBody.addValue("roleId",mDataCatch.getLoginModel()?.roelId.toString()))
            isLoadMore = true
        }
    }

    private fun requestData(){
        mPageRequestBody= PageRequestBody("","0",1)
        mViewModel.getFundByRoleId(mPageRequestBody.addValue("roleId",mDataCatch.getLoginModel()?.roelId.toString()))
    }

    override fun setUpViewModel() {
        super.setUpViewModel()
        mViewModel.mFundsManageModel.observe(this, Observer {
            mPageBaseModel = it as PageBaseModel<FundsManageModel>
            if(mPageBaseModel!!.content != null){
                list = mPageBaseModel!!.content as ArrayList
            }

            if(isLoadMore){
                mFundsManageAdapter.addMoreData(list)
                refreshLayout.finishLoadMore()
            }else{
                mFundsManageAdapter = FundsManageAdapter(activity!!, list)
                recyclerView.adapter = mFundsManageAdapter
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