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
import org.jetbrains.anko.find
import www.mrray.cn.R
import www.mrray.cn.model.gp.HighCustomerModel
import www.mrray.cn.utils.ImageManager
import www.mrray.cn.utils.NumberFormatUtil


/**
 *@author suo
 *@date 2018/10/15
 *@desc: 优质客户适配器
 */
class HighCustomerAdapter(mContext: Context, data: ArrayList<HighCustomerModel>, iSHome: Boolean = false) : RecyclerView.Adapter<HighCustomerAdapter.ViewHolder>() {

    val list: ArrayList<HighCustomerModel> = data
    val inflater: LayoutInflater = LayoutInflater.from(mContext)
    val context: Context = mContext
    val mIsHome: Boolean = iSHome

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)?.inflate(R.layout.item_high_customer, p0, false)!!, context)

    }

    fun addMoreData( moreData : ArrayList<HighCustomerModel>){
        if( list!=null && moreData != null ){
            list.addAll(moreData)
            notifyDataSetChanged()
        }
    }



    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val bean = list[p1]
        if(bean.image.isNullOrEmpty()){
            Glide.with(context)
                    .load(R.mipmap.default_error)
                    .apply(RequestOptions.errorOf(R.mipmap.default_error))
                    .into(p0.image)
        }else{
            Glide.with(context)
                    .load(ImageManager.stringToBitmap(bean.image))
                    .apply(RequestOptions.errorOf(R.mipmap.default_error))
                    .into(p0.image)
        }

        p0.name.text = bean.name
        p0.amount.text = NumberFormatUtil.numberFormatToString(bean.amount)
        p0.industry.text = bean.industry
        p0.fundCount.text = bean.fundCount + "次"
        p0.projectCount.text = bean.projectCount + "次"
        if (mIsHome) {
            p0.recommend.visibility = View.VISIBLE
            p0.chat.visibility = View.GONE
        } else {
            p0.chat.visibility = View.VISIBLE
            p0.recommend.visibility = View.GONE
        }
        p0.itemView.setOnClickListener {
//            //new出EaseChatFragment或其子类的实例
            context.startActivity(Intent(context, ImChatActivity::class.java)
                    .putExtra(EaseConstant.EXTRA_USER_ID, bean.imname).putExtra(EaseConstant.EXTRA_USER_NAME, bean.name))
//            context.toast(context.getString(R.string.no_permission_tip))
        }
    }

    class ViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.find(R.id.image)
        var name: TextView = itemView.find(R.id.name)
        var amount: TextView = itemView.find(R.id.amount)
        var industry: TextView = itemView.find(R.id.industry)
        var fundCount: TextView = itemView.find(R.id.fundCount)
        var projectCount: TextView = itemView.find(R.id.projectCount)
        var recommend: ImageView = itemView.find(R.id.recommend)
        var chat: ImageView = itemView.find(R.id.chat)
    }
}