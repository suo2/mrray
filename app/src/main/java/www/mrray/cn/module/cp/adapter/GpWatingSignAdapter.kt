package www.mrray.cn.module.cp.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import www.mrray.cn.R
import www.mrray.cn.module.cp.holder.GpWatingSignHolder
import www.mrray.cn.module.cp.holder.LpWaitingSignHolder

class GpWatingSignAdapter(val mDataList: ArrayList<Any>) : RecyclerView.Adapter<GpWatingSignHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): GpWatingSignHolder = GpWatingSignHolder(LayoutInflater.from(p0.context)
            .inflate(R.layout.item_waiting_sign_gp, p0, false))

    override fun getItemCount(): Int = mDataList.size

    override fun onBindViewHolder(p0: GpWatingSignHolder, p1: Int) {
    }
}