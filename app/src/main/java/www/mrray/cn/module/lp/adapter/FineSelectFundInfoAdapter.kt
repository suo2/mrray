package www.mrray.cn.module.lp.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import www.mrray.cn.BuildConfig
import www.mrray.cn.R
import www.mrray.cn.model.lp.FineSelectFundModel
import www.mrray.cn.module.web.JSBridgeWebViewActivity
import www.mrray.cn.utils.NumberFormatUtil
import www.mrray.cn.utils.Utils
import kotlin.math.roundToInt

/**
 *@author suo
 *@date 2018/10/15
 *@desc: 精选基金适配器（LP发现中的列表）
 */
class FineSelectFundInfoAdapter(mContext: Context, data: ArrayList<FineSelectFundModel>) : RecyclerView.Adapter<FineSelectFundInfoAdapter.ViewHolder>() {

    val list: ArrayList<FineSelectFundModel> = data
    val inflater: LayoutInflater = LayoutInflater.from(mContext)
    val context: Context = mContext

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)?.inflate(R.layout.item_fineselect_fund_info, p0, false)!!, context)
    }

    fun addMoreData( moreData : ArrayList<FineSelectFundModel>){
        if( list!=null && moreData != null ){
            list.addAll(moreData)
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val bean: FineSelectFundModel = list[p1]
        p0.name.text = bean.name
        Glide.with(context).load(BuildConfig.API_HOST + bean.fundLogo).apply(RequestOptions.errorOf(R.mipmap.default_error)).into(p0.fundLogo)
        p0.investmentTime.text = bean.investmentTime
        p0.startTime.text = Utils.getDateYYYYMMDD(bean.startTime?.toLong())
        p0.fundSize.text = NumberFormatUtil.numberFormatToString(bean.fundSize)
        p0.investmentTrends.text = bean.investmentTrends
        p0.managementAgency.text = bean.managementAgency
        p0.amountInvested.text = NumberFormatUtil.numberFormatToString(bean.amountInvested)
        p0.minInvestmentAmount.text = NumberFormatUtil.numberFormatToString(bean.minInvestmentAmount)
        if (bean.percent.isNullOrEmpty()) {
            p0.progressBar.progress = 0
            p0.progressBarValue.text = "0"
        } else {
            p0.progressBar.progress = bean.percent.toDouble().roundToInt()
            p0.progressBarValue.text = bean.percent
        }

        p0.itemView.setOnClickListener {
            context.startActivity<JSBridgeWebViewActivity>(JSBridgeWebViewActivity.EXTRA_NAME to "基金详情",
                    JSBridgeWebViewActivity.EXTRA_URL to BuildConfig.H5_HOST + "/fundDetail?fundId=" + bean.fundId)
        }
    }

    class ViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.find(R.id.name)
        var fundLogo: ImageView = itemView.find(R.id.fundLogo)
        var investmentTime: TextView = itemView.find(R.id.investmentTime)
        var fundSize: TextView = itemView.find(R.id.fundSize)
        var startTime: TextView = itemView.find(R.id.startTime)
        var investmentTrends: TextView = itemView.find(R.id.investmentTrends)
        var managementAgency: TextView = itemView.find(R.id.managementAgency)
        var minInvestmentAmount: TextView = itemView.find(R.id.minInvestmentAmount)
        var progressBarValue: TextView = itemView.find(R.id.progressBarValue)
        val amountInvested : TextView = itemView.find(R.id.amountInvested)
        var progressBar: ProgressBar = itemView.find(R.id.progressBar)
    }
}