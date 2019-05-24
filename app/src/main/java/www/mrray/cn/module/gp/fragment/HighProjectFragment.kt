package www.mrray.cn.module.gp

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import kotlinx.android.synthetic.main.fragment_high_project.*
import www.mrray.cn.R
import www.mrray.cn.base.BaseViewModelFragment
import www.mrray.cn.customexception.HttpException
import www.mrray.cn.http.requestbody.PageRequestBody
import www.mrray.cn.model.PageBaseModel
import www.mrray.cn.model.gp.HighProjectModel
import www.mrray.cn.module.gp.adapter.HighProjectInfoAdapter
import www.mrray.cn.repository.gp.GpHomeRepository
import www.mrray.cn.viewmodel.gp.GpHomeViewModel


/**
 *@author suo
 *@date 2018/10/17
 *@desc:
 */
class HighProjectFragment : BaseViewModelFragment<GpHomeViewModel, GpHomeRepository>() {

    private  var projectList = ArrayList<HighProjectModel>()

    private lateinit var mHighProjectInfoAdapter: HighProjectInfoAdapter

    private lateinit var mPageRequestBody: PageRequestBody

    private var mPageBaseModel : PageBaseModel<HighProjectModel>? = null


    var isLoadMore : Boolean = false

    companion object {
        fun newInstance() = HighProjectFragment()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_high_project
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
                mViewModel.getProjectPageQuery(mPageRequestBody.getMap())
            }
        }

        refreshLayout.setRefreshHeader(ClassicsHeader(activity))
        refreshLayout.setRefreshFooter(ClassicsFooter(activity!!))
        refreshLayout.setOnRefreshListener {
            refreshLayout.setNoMoreData(false)
            mPageRequestBody.page = 1
            mViewModel.getProjectPageQuery(mPageRequestBody.getMap())
            isLoadMore = false
        }
        refreshLayout.setOnLoadMoreListener {
            if(mPageRequestBody.page== mPageBaseModel!!.totalPage){
                refreshLayout.finishLoadMoreWithNoMoreData()
                return@setOnLoadMoreListener
            }
            mPageRequestBody.page++
            mViewModel.getProjectPageQuery(mPageRequestBody.getMap())
            isLoadMore = true
        }
    }

    private fun requestData(){
        mPageRequestBody= PageRequestBody("","0",1)
        mViewModel.getProjectPageQuery(mPageRequestBody.getMap())
    }

    override fun setUpViewModel() {
        super.setUpViewModel()
        mViewModel.mHighProjectModel.observe(this, Observer {
            mPageBaseModel = it as PageBaseModel<HighProjectModel>
            if(mPageBaseModel!!.content != null){
                projectList = mPageBaseModel!!.content as ArrayList
            }
            if(isLoadMore){
                mHighProjectInfoAdapter.addMoreData(projectList)
                refreshLayout.finishLoadMore()
            }else{
                mHighProjectInfoAdapter = HighProjectInfoAdapter(activity!!, projectList)
                recyclerView.adapter = mHighProjectInfoAdapter
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