package www.mrray.cn.module.gp.adapter

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
import org.jetbrains.anko.toast
import www.mrray.cn.BuildConfig
import www.mrray.cn.R
import www.mrray.cn.model.gp.HighProjectModel
import www.mrray.cn.utils.NumberFormatUtil
import www.mrray.cn.utils.Utils
import kotlin.math.roundToInt

/**
 *@author suo
 *@date 2018/10/15
 *@desc: 优质项目适配器
 */
class HighProjectInfoAdapter(mContext : Context, data : ArrayList<HighProjectModel>) : RecyclerView.Adapter<HighProjectInfoAdapter.ViewHolder>() {

    val list: ArrayList<HighProjectModel> = data
    val inflater: LayoutInflater = LayoutInflater.from(mContext)
    val context: Context = mContext

    override fun getItemCount(): Int {
        return list.size
    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)?.inflate(R.layout.item_high_project_info, p0, false)!!, context)
    }

    fun addMoreData( moreData : ArrayList<HighProjectModel>){
        if( list!=null && moreData != null ){
            list.addAll(moreData)
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val bean: HighProjectModel = list[p1]
        Glide.with(context).load(BuildConfig.API_HOST + bean.logoPath).apply(RequestOptions.errorOf(R.mipmap.default_error)).into(p0.logoPath)
        p0.projectName.text = bean.projectName
        p0.amount.text = NumberFormatUtil.numberFormatToString(bean.amount)
        p0.amountInvested.text = NumberFormatUtil.numberFormatToString(bean.amountInvested)
        p0.address.text = bean.address
        p0.startTime.text = Utils.getDateYYYYMMDD(bean.startTime?.toLong())
        p0.industry.text = bean.industry
        p0.rotation.text = bean.rotation
        if (bean.percent.isNullOrEmpty()) {
            p0.progressBar.progress = 0
            p0.progressBarValue.text = "0"
        } else {
            p0.progressBar.progress = bean.percent.toDouble().roundToInt()
            p0.progressBarValue.text = bean.percent
        }
        p0.itemView.setOnClickListener {
//            context.startActivity<JSBridgeWebViewActivity>(JSBridgeWebViewActivity.EXTRA_NAME to "项目详情",
//                    JSBridgeWebViewActivity.EXTRA_URL to BuildConfig.H5_HOST + "/projectDetail?projectId=" + bean.projectId)
            context.toast(context.getString(R.string.no_permission_tip))
        }
    }

    class ViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView){
        var logoPath: ImageView = itemView.find(R.id.logoPath) as ImageView
        var projectName: TextView = itemView.find(R.id.projectName)
        var amount: TextView = itemView.find(R.id.amount)
        var amountInvested: TextView = itemView.find(R.id.amountInvested)
        var address: TextView = itemView.find(R.id.address)
        var startTime: TextView = itemView.find(R.id.startTime)
        var industry: TextView = itemView.find(R.id.industry)
        var rotation: TextView = itemView.find(R.id.rotation)
        var progressBarValue: TextView = itemView.find(R.id.progressBarValue)
        var progressBar : ProgressBar = itemView.find(R.id.progressBar ) as ProgressBar
    }
}