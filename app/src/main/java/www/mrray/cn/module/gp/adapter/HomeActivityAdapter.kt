package www.mrray.cn.module.gp.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import org.jetbrains.anko.find
import www.mrray.cn.R
import www.mrray.cn.model.gp.HomeActivityModel


/**
 *@author suo
 *@date 2018/10/15
 *@desc: 首页活动模块适配器
 */
class HomeActivityAdapter(mContext : Context, data : ArrayList<HomeActivityModel>) : RecyclerView.Adapter<HomeActivityAdapter.ViewHolder>() {


    val list: ArrayList<HomeActivityModel> = data
    val inflater: LayoutInflater = LayoutInflater.from(mContext)
    val context: Context = mContext


    override fun getItemCount(): Int {
        return list?.size ?:0
    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)?.inflate(R.layout.item_home_activity, p0, false)!!, context)

    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        Glide.with(context).load(list[p1].imgUrl).into(p0.img)
        p0.title.text = list[p1].title
        p0.time.text = list[p1].time
        p0.institution.text = list[p1].institution
        p0.speaker.text = list[p1].speaker
    }

    class ViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView){
        var img: ImageView = itemView.find(R.id.img)
        var title: TextView = itemView.find(R.id.title) as TextView
        var time: TextView = itemView.find(R.id.time) as TextView
        var institution: TextView = itemView.find(R.id.institution) as TextView
        var speaker: TextView = itemView.find(R.id.speaker) as TextView
    }
}