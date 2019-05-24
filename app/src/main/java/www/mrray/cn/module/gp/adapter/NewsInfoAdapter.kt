package www.mrray.cn.module.gp.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import www.mrray.cn.R
import www.mrray.cn.model.gp.NewsInfoModel
import www.mrray.cn.utils.Utils

/**
 *@author suo
 *@date 2018/10/15
 *@desc: 新闻咨询适配器
 */
class NewsInfoAdapter(mContext : Context, data : ArrayList<NewsInfoModel>) : RecyclerView.Adapter<NewsInfoAdapter.ViewHolder>() {

    val list: ArrayList<NewsInfoModel> = data
    val inflater: LayoutInflater = LayoutInflater.from(mContext)
    val context: Context = mContext

    override fun getItemCount(): Int {
        return list.size
    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)?.inflate(R.layout.item_news_info, p0, false)!!, context)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        if (list[p1]==null){return}
//        Glide.with(context).load(list[p1].imgUrl).into(p0.img)
        p0.img.setImageResource(R.mipmap.activity_img1)
        p0.newsTitle.text = list[p1].newsTitle
        p0.time.text = Utils.getDateYYYYMMDD(list[p1].time.toLong())
        p0.from.text = list[p1].from
        p0.readCount.text = list[p1].readCount
    }
    class ViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView){
        var img: ImageView = itemView.findViewById(R.id.img) as ImageView
        var newsTitle: TextView = itemView.findViewById(R.id.newsTitle) as TextView
        var time: TextView = itemView.findViewById(R.id.time) as TextView
        var from: TextView = itemView.findViewById(R.id.from) as TextView
        var readCount: TextView = itemView.findViewById(R.id.readCount) as TextView
    }
}