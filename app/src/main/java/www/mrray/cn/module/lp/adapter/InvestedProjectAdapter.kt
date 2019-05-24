package www.mrray.cn.module.lp.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import www.mrray.cn.R
import www.mrray.cn.model.lp.InvestedProjectModel
import www.mrray.cn.utils.NumberFormatUtil

/**
 *@author suo
 *@date 2018/10/29
 *@desc: 已投项目适配器
 */
class InvestedProjectAdapter(mContext : Context, data : ArrayList<InvestedProjectModel>) : RecyclerView.Adapter<InvestedProjectAdapter.ViewHolder>() {

    val list: ArrayList<InvestedProjectModel> = data
    val inflater: LayoutInflater = LayoutInflater.from(mContext)
    val context: Context = mContext

    override fun getItemCount(): Int {
        return list.size
    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)?.inflate(R.layout.item_invested_project, p0, false)!!, context)
    }

    fun addMoreData( moreData : ArrayList<InvestedProjectModel>){
        if( list!=null && moreData != null ){
            list.addAll(moreData)
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val bean : InvestedProjectModel = list[p1]
        p0.name.text = bean.name
        p0.annualizedIncome.text = bean.annualizedIncome
        p0.currentAddAssets.text =  NumberFormatUtil.numberFormatToString(bean.currentAddAssets)
//        p0.investmentTime.text = Utils.getDateYYYYMMDD(bean.investmentTime.toLong())
//        p0.expectedExitTime.text = Utils.getDateYYYYMMDD(bean.expectedExitTime.toLong())
        p0.investmentTime.text =bean.investmentTime
        p0.expectedExitTime.text = bean.expectedExitTime
        p0.returnMultiple.text = bean.returnMultiple
        p0.shareholdingRatio.text = bean.shareholdingRatio
        p0.currentEnterprise.text = NumberFormatUtil.numberFormatToString(bean.currentEnterprise)
        p0.investmentAmount.text = NumberFormatUtil.numberFormatToString(bean.investmentAmount)
        p0.itemView.setOnClickListener {
//            val intent = Intent(context, JSBridgeWebViewActivity::class.java)
//            intent.putExtra(JSBridgeWebViewActivity.EXTRA_NAME,"已投项目")
//            intent.putExtra(JSBridgeWebViewActivity.EXTRA_URL,BuildConfig.H5_HOST+"/ProjectManage?roleId="+
//                    DataCatchInfoUtils(context.applicationContext).getLoginModel()?.roelId+"&projectId="+bean.id+"&name=已投项目")
//            context.startActivity(intent)
            context.toast(context.getString(R.string.no_permission_tip))
        }
    }
    class ViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView){
        var name: TextView = itemView.find(R.id.name)
        var annualizedIncome: TextView = itemView.find(R.id.annualizedIncome)
        var currentAddAssets: TextView = itemView.find(R.id.currentAddAssets)
        var investmentTime: TextView = itemView.find(R.id.investmentTime)
        var expectedExitTime : TextView = itemView.find(R.id.expectedExitTime)
        var returnMultiple : TextView = itemView.find(R.id.returnMultiple)
        var shareholdingRatio : TextView = itemView.find(R.id.shareholdingRatio)
        var currentEnterprise : TextView = itemView.find(R.id.currentEnterprise)
        var investmentAmount : TextView = itemView.find(R.id.investmentAmount)
    }
}