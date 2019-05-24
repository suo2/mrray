package www.mrray.cn.module.gp

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.View
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import kotlinx.android.synthetic.main.drawerlayout_gp.*
import kotlinx.android.synthetic.main.fragment_high_customer.*
import www.mrray.cn.R
import www.mrray.cn.base.BaseViewModelFragment
import www.mrray.cn.customexception.HttpException
import www.mrray.cn.http.requestbody.PageRequestBody
import www.mrray.cn.model.PageBaseModel
import www.mrray.cn.model.gp.HighCustomerModel
import www.mrray.cn.module.gp.adapter.HighCustomerAdapter
import www.mrray.cn.repository.gp.GpHomeRepository
import www.mrray.cn.viewmodel.gp.GpHomeViewModel

/**
 *@author suo
 *@date 2018/10/17
 *@desc: 优质客户列表
 */
class HighCustomerFragment : BaseViewModelFragment<GpHomeViewModel, GpHomeRepository>() {

    private lateinit var customerList: ArrayList<HighCustomerModel>

    private lateinit var mHighCustomerAdapter: HighCustomerAdapter

    private lateinit var mPageRequestBody: PageRequestBody


    private var mPageBaseModel : PageBaseModel<HighCustomerModel>? = null

    var isLoadMore : Boolean = false

    companion object {
        fun newInstance() = HighCustomerFragment()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_high_customer
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
        more_filter.setOnClickListener {
            if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                drawerLayout.closeDrawer(Gravity.RIGHT)
            } else {
                drawerLayout.openDrawer(Gravity.RIGHT)
            } }

        customerList = ArrayList()
        recyclerView.layoutManager = object : LinearLayoutManager(context) {
        }
        mHighCustomerAdapter = HighCustomerAdapter(activity!!, customerList)
        recyclerView.addItemDecoration(SpaceItemDecoration(20))
        recyclerView.adapter = mHighCustomerAdapter

        filter.setOnTouchListener { v, event -> false
            filter.setFilterListener(event){
                mPageRequestBody.direction = it.sort
                mPageRequestBody.property = it.position.toString()
                mPageRequestBody.page = 1
                mViewModel.getInvestorPageQuery(mPageRequestBody.getMap())
            }
        }

        refreshLayout.setRefreshHeader(ClassicsHeader(activity))
        refreshLayout.setRefreshFooter(ClassicsFooter(activity!!))
        refreshLayout.setOnRefreshListener {
            refreshLayout.setNoMoreData(false)
            mPageRequestBody.page = 1
            mViewModel.getInvestorPageQuery(mPageRequestBody.getMap())
            isLoadMore = false
        }
        refreshLayout.setOnLoadMoreListener {
            if(mPageRequestBody.page== mPageBaseModel!!.totalPage){
                refreshLayout.finishLoadMoreWithNoMoreData()
                return@setOnLoadMoreListener
            }
            mPageRequestBody.page++
            mViewModel.getInvestorPageQuery(mPageRequestBody.getMap())
            isLoadMore = true
        }
    }

    private fun requestData(){
        mPageRequestBody= PageRequestBody("","0",1)
        mViewModel.getInvestorPageQuery(mPageRequestBody.getMap())
    }

    override fun setUpViewModel() {
        super.setUpViewModel()
        mViewModel.mHighCustomerModel.observe(this, Observer {
            mPageBaseModel = it as PageBaseModel<HighCustomerModel>
            if(mPageBaseModel!!.content != null){
                customerList = mPageBaseModel!!.content as ArrayList
            }
            if(isLoadMore){
                mHighCustomerAdapter.addMoreData(customerList)
                refreshLayout.finishLoadMore()
            }else{
                mHighCustomerAdapter = HighCustomerAdapter(activity!!, customerList)
                recyclerView.adapter = mHighCustomerAdapter
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