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
import www.mrray.cn.model.lp.InvestedFundModel
import www.mrray.cn.utils.NumberFormatUtil
import www.mrray.cn.utils.Utils

/**
 *@author suo
 *@date 2018/10/29
 *@desc: 已投基金适配器
 */
class InvestedFundAdapter(mContext : Context, data : ArrayList<InvestedFundModel>) : RecyclerView.Adapter<InvestedFundAdapter.ViewHolder>() {
    val list: ArrayList<InvestedFundModel> = data
    val inflater: LayoutInflater = LayoutInflater.from(mContext)
    val context: Context = mContext

    override fun getItemCount(): Int {
        return list.size
    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)?.inflate(R.layout.item_invested_fund, p0, false)!!, context)
    }
    fun addMoreData( moreData : ArrayList<InvestedFundModel>){
        if( list!=null && moreData != null ){
            list.addAll(moreData)
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val bean : InvestedFundModel = list[p1]
        p0.name.text = bean.name
        p0.annualizedIncome.text = bean.annualizedIncome
        p0.currentAddAssets.text = NumberFormatUtil.numberFormatToString(bean.currentAddAssets)
        p0.investmentTime.text = Utils.getDateYYYYMMDD(bean.investmentTime.toLong())
        p0.expectedExitTime.text = Utils.getDateYYYYMMDD(bean.expectedExitTime.toLong())
        p0.returnMultiple.text = bean.returnMultiple
        p0.investmentAmount.text = NumberFormatUtil.numberFormatToString(bean.investmentAmount)
        p0.currentEnterprise.text = NumberFormatUtil.numberFormatToString(bean.currentEnterprise)
        p0.itemView.setOnClickListener {
//            val intent = Intent(context,JSBridgeWebViewActivity::class.java)
//            intent.putExtra(JSBridgeWebViewActivity.EXTRA_NAME,"已投基金")
//            intent.putExtra(JSBridgeWebViewActivity.EXTRA_URL,BuildConfig.H5_HOST+"/FundManage?roleId="+
//                    DataCatchInfoUtils(context.applicationContext).getLoginModel()?.roelId+"&fundId="+bean.id+"&name=已投基金")
//            context.startActivity(intent)
            context.toast(context.getString(R.string.no_permission_tip))
        }
    }

    class ViewHolder(itemView: View,val context: Context) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        override fun onClick(v: View?) {

        }
        init {
            itemView.setOnClickListener(this)
        }
        var name: TextView = itemView.find(R.id.name)
        var annualizedIncome: TextView = itemView.find(R.id.annualizedIncome)
        var currentAddAssets: TextView = itemView.find(R.id.currentAddAssets)
        var investmentTime: TextView = itemView.find(R.id.investmentTime)
        var expectedExitTime : TextView = itemView.find(R.id.expectedExitTime)
        var returnMultiple : TextView = itemView.find(R.id.returnMultiple)
        var investmentAmount : TextView = itemView.find(R.id.investmentAmount)
        var currentEnterprise : TextView = itemView.find(R.id.currentEnterprise)
    }



}