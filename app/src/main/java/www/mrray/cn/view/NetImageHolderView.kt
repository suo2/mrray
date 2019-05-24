package www.mrray.cn.view

import android.support.v4.app.FragmentActivity
import android.view.View
import android.widget.ImageView
import com.bigkoo.convenientbanner.holder.Holder
import com.bumptech.glide.Glide
import www.mrray.cn.R

/**
 *@author suo
 *@date 2018/10/12
 *@desc: 网络图片Holder
 */
class NetImageHolderView(itemView: View, private val mContext: FragmentActivity?) : Holder<String>(itemView) {

    private var mImageView: ImageView? = null

    override fun initView(itemView: View) {
        mImageView = itemView.findViewById(R.id.ivPost)
    }

    override fun updateUI(data: String) {
        Glide.with(this.mContext!!).load(data).into(mImageView!!)
    }
}
