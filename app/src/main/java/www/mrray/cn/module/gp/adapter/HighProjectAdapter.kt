package www.mrray.cn.module.gp.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import www.mrray.cn.R
import www.mrray.cn.model.gp.HighProjectModel
import www.mrray.cn.utils.NumberFormatUtil

/**
 *@author suo
 *@date 2018/10/15
 *@desc: 优质项目适配器
 */
class HighProjectAdapter(mContext: Context, data: ArrayList<HighProjectModel>) : RecyclerView.Adapter<HighProjectAdapter.ViewHolder>() {

    val list: ArrayList<HighProjectModel> = data
    val inflater: LayoutInflater = LayoutInflater.from(mContext)
    val context: Context = mContext

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)?.inflate(R.layout.item_high_project, p0, false)!!, context)

    }

    fun addMoreData( moreData : ArrayList<HighProjectModel>){
        if( list!=null && moreData != null ){
            list.addAll(moreData)
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val mHighProjectModel: HighProjectModel = list[p1]
        p0.projectName.text = mHighProjectModel.projectName

        val amountArray: Array<String?> = NumberFormatUtil.numberFormatToArray(mHighProjectModel.amount)
        p0.amount.text = amountArray[0]
        p0.amountUnit.text = amountArray[1]

        val valuationArray: Array<String?> = NumberFormatUtil.numberFormatToArray(mHighProjectModel.valuation)
        p0.valuation.text = valuationArray[0]
        p0.valuationUnit.text = valuationArray[1]

        p0.industry.text = mHighProjectModel.industry

        p0.itemView.setOnClickListener {
//            context.startActivity<JSBridgeWebViewActivity>(JSBridgeWebViewActivity.EXTRA_NAME to "项目详情",
//                    JSBridgeWebViewActivity.EXTRA_URL to BuildConfig.H5_HOST + "/projectDetail?projectId=" + mHighProjectModel.projectId)
            context.toast(context.getString(R.string.no_permission_tip))
        }
    }

    class ViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView) {
        var projectName: TextView = itemView.find(R.id.projectName)
        var amount: TextView = itemView.find(R.id.amount)
        var amountUnit: TextView = itemView.find(R.id.amountUnit)
        var valuation: TextView = itemView.find(R.id.valuation)
        var valuationUnit: TextView = itemView.find(R.id.valuationUnit)
        var industry: TextView = itemView.find(R.id.industry)

    }
}