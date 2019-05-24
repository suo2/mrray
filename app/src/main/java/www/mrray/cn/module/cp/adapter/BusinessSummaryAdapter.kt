package www.mrray.cn.module.cp.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import www.mrray.cn.R
import www.mrray.cn.model.cp.BusinessSummaryModel
import www.mrray.cn.module.cp.holder.BusinessSummaryHolder

/**
 * 经营总结
 */
class BusinessSummaryAdapter(private val mDataList: ArrayList<BusinessSummaryModel>) : RecyclerView.Adapter<BusinessSummaryHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): BusinessSummaryHolder =
            BusinessSummaryHolder(LayoutInflater.from(p0.context)
                    .inflate(R.layout.item_business_summary, p0, false))

    override fun getItemCount(): Int = mDataList.size

    override fun onBindViewHolder(p0: BusinessSummaryHolder, p1: Int) {
        p0.onBind(mDataList[p1])
    }
}