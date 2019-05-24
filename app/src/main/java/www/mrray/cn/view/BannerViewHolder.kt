package www.mrray.cn.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

import com.zhouwei.mzbanner.holder.MZViewHolder

import www.mrray.cn.R

/**
 * @author suo
 * @date 2018/10/26
 * @desc:
 */
class BannerViewHolder(context: Context) : MZViewHolder<String> {
    var mContext : Context = context
    private var mImageView: ImageView? = null
    override fun createView(context: Context): View {
        // 返回页面布局
        val view = LayoutInflater.from(context).inflate(R.layout.banner_item, null)
        mImageView = view.findViewById<View>(R.id.banner_image) as ImageView
        return view
    }

    override fun onBind(context: Context, position: Int, data: String?) {
        // 数据绑定
        Glide.with(this.mContext!!).load(data).into(mImageView!!)
    }
}