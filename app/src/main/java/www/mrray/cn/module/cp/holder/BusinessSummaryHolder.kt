package www.mrray.cn.module.cp.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import org.jetbrains.anko.find
import www.mrray.cn.R
import www.mrray.cn.model.cp.BusinessSummaryModel

class BusinessSummaryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val businessContent: TextView = itemView.find<TextView>(R.id.business_content)
    val businessTime: TextView = itemView.find<TextView>(R.id.business_time_txt)
    val businessTitle: TextView = itemView.find(R.id.business_title)

    fun onBind(data: BusinessSummaryModel) {
        businessContent.text = data.businessSummary
        businessTime.text = data.createTime
        businessTitle.text = data.title
    }
}