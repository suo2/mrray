package www.mrray.cn.module.gp.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import www.mrray.cn.R
import www.mrray.cn.model.gp.HomeHeadModel

/**
 *@author suo
 *@date 2018/10/15
 *@desc: 首页grid展示适配器
 */
class HomeGridAdapter(mContext : Context , data : ArrayList<HomeHeadModel>) : RecyclerView.Adapter<HomeGridAdapter.ViewHolder>() {

    var list: ArrayList<HomeHeadModel>? = null
    var inflater: LayoutInflater? = null
    var context: Context? = null

    init {
        this.list = data
        this.inflater = LayoutInflater.from(mContext!!)
        this.context = mContext
    }

    override fun getItemCount(): Int {
        return list?.size ?:0
    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context!!)?.inflate(R.layout.item_home_grid, p0, false), context!!)
    }


    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
//        p0!!.name.text = list!![p1].name
//        p0!!.number.text = list!![p1].number
    }

    class ViewHolder(itemView: View?, context: Context) : RecyclerView.ViewHolder(itemView!!){
        var number: TextView = itemView!!.findViewById(R.id.number) as TextView
        var name: TextView = itemView!!.findViewById(R.id.name) as TextView
    }
}