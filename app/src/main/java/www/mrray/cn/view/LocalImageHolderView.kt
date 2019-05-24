package www.mrray.cn.view

import android.view.View
import android.widget.ImageView

import com.bigkoo.convenientbanner.holder.Holder

import www.mrray.cn.R

/**
 *@author suo
 *@date 2018/10/12
 *@desc: 本地图片Holder
 */
class LocalImageHolderView(itemView: View) : Holder<Int>(itemView) {
    private var imageView: ImageView? = null

    override fun initView(itemView: View) {
        imageView = itemView.findViewById(R.id.ivPost)
    }

    override fun updateUI(data: Int?) {
        imageView!!.setImageResource(data!!)
    }
}
