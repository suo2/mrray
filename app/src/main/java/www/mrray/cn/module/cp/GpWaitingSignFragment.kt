package www.mrray.cn.module.cp

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.fragment_waiting_sign.*
import www.mrray.cn.R
import www.mrray.cn.base.BaseViewModelFragment
import www.mrray.cn.module.cp.adapter.GpWatingSignAdapter
import www.mrray.cn.repository.cp.WaitingSignRepository
import www.mrray.cn.viewmodel.cp.WaitingSignViewModel

class GpWaitingSignFragment : BaseViewModelFragment<WaitingSignViewModel, WaitingSignRepository>() {

    val mDataList =ArrayList<Any>()
    val mAdapter = GpWatingSignAdapter(mDataList)
    override fun getLayoutId(): Int {
        return R.layout.fragment_waiting_sign
    }

    override fun init(view: View, savedInstanceState: Bundle?) {
        super.init(view, savedInstanceState)
        mDataList.add("")
        mDataList.add("")
        mDataList.add("")
        mDataList.add("")
        mDataList.add("")
        mDataList.add("")
        mDataList.add("")

        initRecyclerView()
    }

    private fun initRecyclerView() {
        recycler_view.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        recycler_view.adapter = mAdapter
    }
}