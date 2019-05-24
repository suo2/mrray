package www.mrray.cn.module.lp.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import www.mrray.cn.R
import www.mrray.cn.model.lp.InvestingProjectModel
import www.mrray.cn.utils.NumberFormatUtil
import www.mrray.cn.utils.Utils
import kotlin.math.roundToInt

/**
 *@author suo
 *@date 2018/10/29
 *@desc: 投资中项目适配器
 */
class InvestingProjectAdapter(mContext : Context, data : ArrayList<InvestingProjectModel>) : RecyclerView.Adapter<InvestingProjectAdapter.ViewHolder>() {

    val list: ArrayList<InvestingProjectModel> = data
    val inflater: LayoutInflater = LayoutInflater.from(mContext)
    val context: Context = mContext

    override fun getItemCount(): Int {
        return list.size
    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)?.inflate(R.layout.item_investing_project, p0, false)!!, context)

    }

    fun addMoreData( moreData : ArrayList<InvestingProjectModel>){
        if( list!=null && moreData != null ){
            list.addAll(moreData)
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val bean : InvestingProjectModel = list[p1]
        p0.name.text = bean.name
        p0.address.text = bean.address
        p0.amount.text = NumberFormatUtil.numberFormatToString(bean.amount)
        p0.startTime.text = Utils.getDateYYYYMMDD(bean.startTime.toLong())
        p0.industry.text = bean.industry
        p0.rotation.text = bean.rotation
        p0.completedAmount.text = NumberFormatUtil.numberFormatToString(bean.completedAmount)
        if(bean.percent.isNullOrEmpty()){
            p0.progressBar.progress = 0
            p0.progressBarValue.text = "0"+"%"
        }else{
            p0.progressBar.progress = bean.percent.toDouble().roundToInt()
            p0.progressBarValue.text = bean.percent+"%"
        }
        p0.itemView.setOnClickListener {
//            val intent = Intent(context, JSBridgeWebViewActivity::class.java)
//            intent.putExtra(JSBridgeWebViewActivity.EXTRA_NAME,"投资中项目")
//            intent.putExtra(JSBridgeWebViewActivity.EXTRA_URL, BuildConfig.H5_HOST+"/LPProcess?roleId="+
//                    DataCatchInfoUtils(context.applicationContext).getLoginModel()?.roelId+"&projectId="+bean.id+"&name投资中项目")
//            context.startActivity(intent)
            context.toast(context.getString(R.string.no_permission_tip))
        }
    }

    class ViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView){
        var name: TextView = itemView.find(R.id.name)
        var address: TextView = itemView.find(R.id.address)
        var amount: TextView = itemView.find(R.id.amount)
        var startTime: TextView = itemView.find(R.id.startTime)
        var industry : TextView = itemView.find(R.id.industry)
        var rotation : TextView = itemView.find(R.id.rotation)
        var completedAmount : TextView = itemView.find(R.id.completedAmount)
        var progressBarValue: TextView = itemView.find(R.id.progressBarValue)
        var progressBar : ProgressBar = itemView.find(R.id.progressBar )
    }
}