package www.mrray.cn.module.gp.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import www.mrray.cn.R

class GpDetailHistoryAdapter(val mDataList:ArrayList<String>) : RecyclerView.Adapter<GpDetailHistoryAdapter.GpDetailHistoryHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): GpDetailHistoryHolder = GpDetailHistoryHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_gp_detail_history,p0,false))

    override fun getItemCount(): Int =mDataList.size


    override fun onBindViewHolder(p0: GpDetailHistoryHolder, p1: Int) {
        p0.onBind("")
    }

    class GpDetailHistoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        public fun onBind(data:String){}
    }
}