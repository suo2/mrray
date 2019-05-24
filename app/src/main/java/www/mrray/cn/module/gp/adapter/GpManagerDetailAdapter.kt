package www.mrray.cn.module.gp.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.jetbrains.anko.find
import www.mrray.cn.R
import www.mrray.cn.model.gp.GpManagerDetailModel

class GpManagerDetailAdapter(private val mDataList: ArrayList<GpManagerDetailModel>) : RecyclerView.Adapter<GpManagerDetailAdapter.GpManagerDetailHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): GpManagerDetailHolder = GpManagerDetailHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_gp_manager_detail, p0, false))

    override fun getItemCount(): Int = mDataList.size

    override fun onBindViewHolder(p0: GpManagerDetailHolder, p1: Int) {
        p0.onBind(mDataList[p1])
    }

    inner class GpManagerDetailHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val gp_manager_detail_time = itemView.find<TextView>(R.id.gp_manager_detail_time)
        val gp_manager_detail_content = itemView.find<TextView>(R.id.gp_manager_detail_content)
        fun onBind(data: GpManagerDetailModel) {
            gp_manager_detail_content.text = data.content
            gp_manager_detail_time.text = data.time
        }

    }
}