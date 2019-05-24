package www.mrray.cn.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import org.jetbrains.anko.alignParentLeft
import org.jetbrains.anko.alignParentRight
import org.jetbrains.anko.centerVertically
import www.mrray.cn.R
import www.mrray.cn.utils.ScreenUtils

/**
 *@author suo
 *@date 2018/10/12
 *@desc: GP首页中的小title布局
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class HomeLinearLayout: RelativeLayout {

    private var name : String = ""

    private var title : TextView? = null

    private var more : TextView? = null

    private var textDrawable : Drawable? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(attrs)
    }
    fun initView (attrs : AttributeSet){
       val typedArray = context.obtainStyledAttributes(attrs, R.styleable.HomeLinearLayout)
        name = typedArray.getString(R.styleable.HomeLinearLayout_showName)?:""

        val layoutParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        setPadding(ScreenUtils.dip2px(context, 15f), ScreenUtils.dip2px(context, 3f), ScreenUtils.dip2px(context, 15f), ScreenUtils.dip2px(context, 3f))
        layoutParams.centerVertically()
        setLayoutParams(layoutParams)

        title = TextView(context)
        title!!.setTextColor(ContextCompat.getColor(context, R.color.text_color))
        title!!.text=name
        val titleLayoutParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        titleLayoutParams.alignParentLeft()
        addView(title,titleLayoutParams)

        more = TextView(context)
        more!!.setTextColor(ContextCompat.getColor(context, R.color.text_color))
        more!!.text = "查看更多"
        val moreLayoutParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        moreLayoutParams.alignParentRight()

        textDrawable = ContextCompat.getDrawable(context,R.mipmap.ic_launcher)
        more!!.setCompoundDrawablesWithIntrinsicBounds(null,null, textDrawable, null)
        more!!.compoundDrawablePadding = 4
        addView(more,moreLayoutParams)
    }

    private var clickListener: MoreClickListener? = null

    fun setMoreClickListener(clickListener: MoreClickListener) {
        this.clickListener = clickListener
    }

    interface MoreClickListener {
        fun onMore()
    }
}