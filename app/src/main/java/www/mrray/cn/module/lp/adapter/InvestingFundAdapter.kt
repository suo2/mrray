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
import www.mrray.cn.model.lp.InvestingFundModel
import www.mrray.cn.utils.NumberFormatUtil
import www.mrray.cn.utils.Utils
import kotlin.math.roundToInt

/**
 *@author suo
 *@date 2018/10/29
 *@desc: 投资中基金适配器
 */
class InvestingFundAdapter(mContext : Context, data : ArrayList<InvestingFundModel>) : RecyclerView.Adapter<InvestingFundAdapter.ViewHolder>() {

    val list: ArrayList<InvestingFundModel> = data
    val inflater: LayoutInflater = LayoutInflater.from(mContext)
    val context: Context = mContext

    override fun getItemCount(): Int {
        return list.size
    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)?.inflate(R.layout.item_investing_fund, p0, false)!!, context)
    }

    fun addMoreData( moreData : ArrayList<InvestingFundModel>){
        if( list!=null && moreData != null ){
            list.addAll(moreData)
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val bean : InvestingFundModel = list[p1]
        p0.name.text = bean.name
        p0.amountInvested.text = NumberFormatUtil.numberFormatToString(bean.amountInvested)
        p0.fundSize.text = NumberFormatUtil.numberFormatToString(bean.fundSize)
//        p0.investmentTime.text = Utils.getDateYYYYMMDD(bean.investmentTime.toLong())
        p0.investmentTime.text = bean.investmentTime
        p0.startTime.text = Utils.getDateYYYYMMDD(bean.startTime?.toLong())
        p0.investmentTrends.text = bean.investmentTrends
        p0.managementAgency.text = bean.managementAgency
        p0.minInvestmentAmount.text = NumberFormatUtil.numberFormatToString(bean.minInvestmentAmount)
        if(bean.percent.isNullOrEmpty()){
            p0.progressBar.progress = 0
            p0.progressBarValue.text = "0"+"%"
        }else{
            p0.progressBar.progress = bean.percent.toDouble().roundToInt()
            p0.progressBarValue.text = bean.percent+"%"
        }

        p0.itemView.setOnClickListener {
//            val intent = Intent(context, JSBridgeWebViewActivity::class.java)
//            intent.putExtra(JSBridgeWebViewActivity.EXTRA_NAME,"投资中基金")
//            intent.putExtra(JSBridgeWebViewActivity.EXTRA_URL, BuildConfig.H5_HOST+"/PurchaseFund?fundId="+
//                    DataCatchInfoUtils(context.applicationContext).getLoginModel()?.roelId+"&roleId="+bean.id+"&name=投资中基金")
//            context.startActivity(intent)
            context.toast(context.getString(R.string.no_permission_tip))
        }
    }

    class ViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView){
        var name: TextView = itemView.find(R.id.name)
        var amountInvested: TextView = itemView.find(R.id.amountInvested)
        var fundSize: TextView = itemView.find(R.id.fundSize)
        var investmentTime: TextView = itemView.find(R.id.investmentTime)
        var startTime: TextView = itemView.find(R.id.startTime)
        var investmentTrends : TextView = itemView.find(R.id.investmentTrends)
        var managementAgency : TextView = itemView.find(R.id.managementAgency)
        var minInvestmentAmount : TextView = itemView.find(R.id.minInvestmentAmount)
        var progressBarValue: TextView = itemView.find(R.id.progressBarValue)
        var progressBar : ProgressBar = itemView.find(R.id.progressBar )
    }
}