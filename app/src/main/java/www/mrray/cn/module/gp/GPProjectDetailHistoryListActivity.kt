package www.mrray.cn.module.gp

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_gp_detail_history.*
import www.mrray.cn.base.BaseViewModelActivity
import www.mrray.cn.repository.gp.GpDetailRepository
import www.mrray.cn.repository.gp.GpHomeRepository
import www.mrray.cn.viewmodel.gp.GpDetailViewModel
import www.mrray.cn.R
import www.mrray.cn.module.gp.adapter.GpDetailHistoryAdapter
import java.util.ArrayList

class GPProjectDetailHistoryListActivity : BaseViewModelActivity<GpDetailViewModel, GpDetailRepository>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_gp_detail_history
    }

    override fun init(savedInstanceState: Bundle?) {
        super.init(savedInstanceState)
        initRecyclerView()
        title_bar.setLeftImgListener {
            finish()
        }
    }

    private fun initRecyclerView() {
        var dataList = ArrayList<String>()
        dataList.add("")
        dataList.add("")
        dataList.add("")
        dataList.add("")
        dataList.add("")
        dataList.add("")
        dataList.add("")
        dataList.add("")
        dataList.add("")
        dataList.add("")

        recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler_view.adapter = GpDetailHistoryAdapter(dataList)
    }
}