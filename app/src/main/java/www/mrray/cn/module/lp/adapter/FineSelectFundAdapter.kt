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
import www.mrray.cn.model.lp.FineSelectFundModel
import www.mrray.cn.utils.NumberFormatUtil
import www.mrray.cn.view.ProgressWheel

/**
 *@author suo
 *@date 2018/10/15
 *@desc: 精选基金适配器
 */
class FineSelectFundAdapter(mContext : Context, data : ArrayList<FineSelectFundModel>) : RecyclerView.Adapter<FineSelectFundAdapter.ViewHolder>() {

    val list: ArrayList<FineSelectFundModel> = data
    val inflater: LayoutInflater = LayoutInflater.from(mContext)
    val context: Context = mContext

    override fun getItemCount(): Int {
        return list.size
    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)?.inflate(R.layout.item_fineselect_fund, p0, false)!!, context)

    }

    fun addMoreData( moreData : ArrayList<FineSelectFundModel>){
        if( list!=null && moreData != null ){
            list.addAll(moreData)
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val bean : FineSelectFundModel = list[p1]
        p0.name.text = bean.name
        p0.investmentTrends.text = bean.investmentTrends
        val amountArray: Array<String?> = NumberFormatUtil.numberFormatToArray(bean.amountInvested)
        p0.amount.text = amountArray[0]
        p0.amountUnit.text = amountArray[1]
        p0.investmentTime.text = bean.investmentTime
        if(!bean.percent.isNullOrEmpty()){
            p0.percent.setPercentage(Math.floor(bean.percent.toDouble()).toString())
            p0.percent.setStepCountText(Math.round(bean.percent.toDouble()).toString())
        }

        p0.itemView.setOnClickListener {
//            context.startActivity<JSBridgeWebViewActivity>(JSBridgeWebViewActivity.EXTRA_NAME to "基金详情",
//                    JSBridgeWebViewActivity.EXTRA_URL to BuildConfig.H5_HOST + "/fundDetail?fundId=" + bean.fundId)
            context.toast(context.getString(R.string.no_permission_tip))
        }
    }

    class ViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView){
        var name: TextView = itemView.find(R.id.name)
        var investmentTrends: TextView = itemView.find(R.id.investmentTrends)
        var amount: TextView = itemView.find(R.id.amount)
        var amountUnit: TextView = itemView.find(R.id.amountUnit)
        var investmentTime: TextView = itemView.find(R.id.investmentTime)
        var percent : ProgressWheel = itemView.find(R.id.percent)
    }
}