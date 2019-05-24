package www.mrray.cn.module.gp.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import www.mrray.cn.R
import www.mrray.cn.model.gp.FundsManageModel
import www.mrray.cn.module.gp.activity.GpManagerDetailActivity

/**
 *@author suo
 *@date 2018/10/15
 *@desc: 基金管理适配器
 */
class FundsManageAdapter(mContext: Context, data: ArrayList<FundsManageModel>) : RecyclerView.Adapter<FundsManageAdapter.ViewHolder>() {

    val list: ArrayList<FundsManageModel> = data
    val inflater: LayoutInflater = LayoutInflater.from(mContext)
    val context: Context = mContext

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)?.inflate(R.layout.item_funds_manage, p0, false)!!, context)

    }

    fun addMoreData( moreData : ArrayList<FundsManageModel>){
        if( list!=null && moreData != null ){
            list.addAll(moreData)
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.fundName.text = list[p1].fundName
        p0.investCompanyCount.text = list[p1].investCompanyCount+ "家"
        p0.lpCount.text = list[p1].lpCount + "位"
        p0.annualizedIncome.text = list[p1].annualizedIncome
        p0.itemView.setOnClickListener {
            context.startActivity<GpManagerDetailActivity>(GpManagerDetailActivity.EXTRA_FOUND_ID
                    to list[p1].fundId,GpManagerDetailActivity.EXTRA_FOUND_NAME to list[p1].fundName)
        }
    }

    class ViewHolder(itemView: View, val context: Context) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        override fun onClick(v: View?) {
        }
        init {
            itemView.setOnClickListener(this)
        }
        var fundName: TextView = itemView.find(R.id.fundName)
        var investCompanyCount: TextView = itemView.find(R.id.investCompanyCount)
        var lpCount: TextView = itemView.find(R.id.lpCount)
        var annualizedIncome: TextView = itemView.find(R.id.annualizedIncome)
    }
}