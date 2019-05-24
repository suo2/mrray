package www.mrray.cn.module.cp.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.jetbrains.anko.find
import www.mrray.cn.R
import www.mrray.cn.model.cp.ProjectManageInfoModel
import www.mrray.cn.utils.Utils

/**
 *@author suo
 *@date 2018/11/5
 *@desc: 相关文件
 */
class ProjectEventAdapter(mContext : Context, data : ArrayList<ProjectManageInfoModel.ProjectEvent>) : RecyclerView.Adapter<ProjectEventAdapter.ViewHolder>() {

    val list: ArrayList<ProjectManageInfoModel.ProjectEvent> = data
    val inflater: LayoutInflater = LayoutInflater.from(mContext)
    val context: Context = mContext

    override fun getItemCount(): Int {
        return list.size
    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)?.inflate(R.layout.item_relevant_file, p0, false)!!, context)

    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val bean : ProjectManageInfoModel.ProjectEvent = list[p1]
        p0.eventName.text = bean.eventName
        p0.fileName.text = bean.fileName
        p0.time.text = Utils.getDateYYYYMMDD(bean.time.toLong())
    }

    class ViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView){
        var eventName: TextView = itemView.find(R.id.eventName)
        var time: TextView = itemView.find(R.id.time)
        var fileName: TextView = itemView.find(R.id.fileName)
    }
}