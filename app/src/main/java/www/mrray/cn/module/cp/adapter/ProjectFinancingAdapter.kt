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
import www.mrray.cn.utils.NumberFormatUtil
import www.mrray.cn.utils.Utils

/**
 *@author suo
 *@date 2018/11/2
 *@desc: 项目融资情况
 */
class ProjectFinancingAdapter(mContext : Context, data : ArrayList<ProjectManageInfoModel.Project>) : RecyclerView.Adapter<ProjectFinancingAdapter.ViewHolder>() {

    val list: ArrayList<ProjectManageInfoModel.Project> = data
    val inflater: LayoutInflater = LayoutInflater.from(mContext)
    val context: Context = mContext

    override fun getItemCount(): Int {
        return list.size
    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)?.inflate(R.layout.item_project_financing, p0, false)!!, context)

    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val bean : ProjectManageInfoModel.Project = list[p1]
        p0.rotation.text = bean.rotation
        p0.percent.text = bean.percent + "%"
        p0.releaseRate.text = bean.releaseRate+ "%"
        p0.startTime.text = Utils.getDateYYYYMMDD(bean.startTime.toLong())
        p0.financeAmount.text = NumberFormatUtil.numberFormatToString(bean.financeAmount)
        p0.valuation.text = NumberFormatUtil.numberFormatToString(bean.valuation)
    }

    class ViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView){
        var rotation: TextView = itemView.find(R.id.rotation)
        var percent: TextView = itemView.find(R.id.percent)
        var releaseRate: TextView = itemView.find(R.id.releaseRate)
        var financeAmount : TextView = itemView.find(R.id.financeAmount)
        var valuation : TextView = itemView.find(R.id.valuation)
        var startTime : TextView = itemView.find(R.id.startTime)
    }
}