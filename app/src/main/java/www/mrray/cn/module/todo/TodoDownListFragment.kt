package www.mrray.cn.module.todo

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.fragment_file_list.*
import www.mrray.cn.R
import www.mrray.cn.base.BaseViewModelFragment
import www.mrray.cn.model.todo.TodoModel
import www.mrray.cn.module.todo.adapter.TodoDownListAdapter
import www.mrray.cn.repository.todo.TodoRepository
import www.mrray.cn.viewmodel.todo.TodoViewModel

/**
 * 文件管理
 */
class TodoDownListFragment : BaseViewModelFragment<TodoViewModel, TodoRepository>() {

    companion object {
        const val EXTRA_PRODUCT_ID = "productId"
    }

    val mListData: ArrayList<TodoModel> = ArrayList()
    override fun getLayoutId(): Int {
        return R.layout.fragment_file_list
    }

    override fun init(view: View, savedInstanceState: Bundle?) {
        super.init(view, savedInstanceState)
        initData()
        initRecyclerView()
    }

    private fun initData() {
        mViewModel.getEventInfo(0, 1)
    }

    override fun setUpViewModel() {
        mViewModel.mDataModel.observe(this, Observer {

        })
    }

    private fun initRecyclerView() {
        mListData.add(TodoModel("工商管理", "2018.04.21", "合同商定事项", "2018年10月1日前，完成工商变更工作", 72, ""))
        mListData.add(TodoModel("工商管理", "2018.04.21", "合同商定事项", "2018年10月1日前，完成工商变更工作", 72, ""))
        mListData.add(TodoModel("工商管理", "2018.04.21", "合同商定事项", "2018年10月1日前，完成工商变更工作", 72, ""))
        mListData.add(TodoModel("工商管理", "2018.04.21", "合同商定事项", "2018年10月1日前，完成工商变更工作", 72, ""))
        mListData.add(TodoModel("工商管理", "2018.04.21", "合同商定事项", "2018年10月1日前，完成工商变更工作", 72, ""))
        mListData.add(TodoModel("工商管理", "2018.04.21", "合同商定事项", "2018年10月1日前，完成工商变更工作", 72, ""))

        recycler_view.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycler_view.adapter = TodoDownListAdapter(mListData)
    }

}