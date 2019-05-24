package www.mrray.cn.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ViewFlipper
import www.mrray.cn.R

/**
 * @author suo
 * @date 2018/10/18
 * @desc: 首页向上滚动广告
 */
class UPMarqueeView(context: Context, attrs: AttributeSet) : ViewFlipper(context, attrs) {

    private var mContext: Context? = null
    private val isSetAnimDuration = false
    private val interval = 2000
    /**
     * 动画时间
     */
    private val animDuration = 500

    /**
     * 点击
     */
    private var onItemClickListener: OnItemClickListener? = null

    init {
        init(context, attrs, 0)
    }

    private fun init(context: Context, attrs: AttributeSet, defStyleAttr: Int) {
        this.mContext = context
        setFlipInterval(interval)
        val animIn = AnimationUtils.loadAnimation(mContext, R.anim.anim_marquee_in)
        if (isSetAnimDuration) animIn.duration = animDuration.toLong()
        inAnimation = animIn
        val animOut = AnimationUtils.loadAnimation(mContext, R.anim.anim_marquee_out)
        if (isSetAnimDuration) animOut.duration = animDuration.toLong()
        outAnimation = animOut
    }


    /**
     * 设置循环滚动的View数组
     *
     * @param views
     */
    fun setViews(views: List<View>?) {
        if (views == null || views.isEmpty()) return
        removeAllViews()
        for (i in views.indices) {
            //设置监听回调
            views[i].setOnClickListener {
                if (onItemClickListener != null) {
                    onItemClickListener!!.onItemClick(i, views[i])
                }
            }
            addView(views[i])
        }
        startFlipping()
    }

    /**
     * 设置监听接口
     * @param onItemClickListener
     */
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    /**
     * item_view的接口
     */
    interface OnItemClickListener {
        fun onItemClick(position: Int, view: View)
    }
}
