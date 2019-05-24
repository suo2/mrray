package www.mrray.cn.module.gp.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hyphenate.easeui.EaseConstant
import com.hyphenate.easeui.ui.im.ImChatActivity
import www.mrray.cn.R
import www.mrray.cn.model.gp.LpManageModel
import www.mrray.cn.utils.ImageManager
import www.mrray.cn.utils.NumberFormatUtil

/**
 *@author suo
 *@date 2018/10/17
 *@desc: LP管理适配器
 */
class LpManageAdapter(mContext : Context, data : ArrayList<LpManageModel>) : RecyclerView.Adapter<LpManageAdapter.ViewHolder>() {

    val list: ArrayList<LpManageModel> = data
    val inflater: LayoutInflater = LayoutInflater.from(mContext)
    val context: Context = mContext

    override fun getItemCount(): Int {
        return list.size
    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)?.inflate(R.layout.item_lp_manage, p0, false)!!, context)
    }

    fun addMoreData( moreData : ArrayList<LpManageModel>){
        if( list!=null && moreData != null ){
            list.addAll(moreData)
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val bean : LpManageModel = list[p1]
//        Glide.with(context).load(BuildConfig.API_HOST + bean.headImage).apply(RequestOptions.errorOf(R.mipmap.default_error)).into(p0.headImage)
        if(bean.headImage.isNullOrEmpty()){
            Glide.with(context)
                    .load(R.mipmap.default_error)
                    .apply(RequestOptions.errorOf(R.mipmap.default_error))
                    .into(p0.headImage)
        }else{
            Glide.with(context)
                    .load(ImageManager.stringToBitmap(bean.headImage))
                    .apply(RequestOptions.errorOf(R.mipmap.default_error))
                    .into(p0.headImage)
        }

        p0.name.text = bean.name
        p0.fundName.text = bean.fundName
        p0.investmentShare.text = bean.investmentShare + "%"
        p0.investmentAmount.text = NumberFormatUtil.numberFormatToString(bean.investmentAmount)

        p0.enterpriseAssets.text = NumberFormatUtil.numberFormatToString(bean.enterpriseAssets)

        p0.addAssets.text = NumberFormatUtil.numberFormatToString(bean.addAssets)
        p0.itemView.setOnClickListener {
            //new出EaseChatFragment或其子类的实例
            context.startActivity(Intent(context, ImChatActivity::class.java).putExtra(EaseConstant.EXTRA_USER_ID, bean.imname))
//            context.toast(context.getString(R.string.no_permission_tip))
        }
    }

    class ViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView){
        var headImage: ImageView = itemView.findViewById(R.id.headImage) as ImageView
        var name: TextView = itemView.findViewById(R.id.name) as TextView
        var fundName: TextView = itemView.findViewById(R.id.fundName) as TextView
        var investmentAmount: TextView = itemView.findViewById(R.id.investmentAmount) as TextView
        var addAssets: TextView = itemView.findViewById(R.id.addAssets) as TextView
        var investmentShare: TextView = itemView.findViewById(R.id.investmentShare) as TextView
        var enterpriseAssets: TextView = itemView.findViewById(R.id.enterpriseAssets) as TextView
    }
}