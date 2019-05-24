package www.mrray.cn.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import org.jetbrains.anko.find
import www.mrray.cn.R

/**
 *@author suo
 *@date 2018/10/18
 *@desc: GP发布基金中所用的
 */
class CustomRelativeLayout : FrameLayout {

    private var mTvName: String = ""
    private var mEvContentHint: String = ""
    //该项是否是必填项 默认必填
    private var mIsRequired : Boolean = true
    private var mIsRightImg : Boolean = false
    private var mIsRightUnit : Boolean = false
    private var mEvLines : Int = 1

    private lateinit var tvName: TextView
    private lateinit var evContent: EditText
    private lateinit var isRequired: TextView
    private lateinit var isRightImg: ImageView
    private lateinit var isRightUnit: TextView

    constructor(context: Context) : super(context)
    constructor(context: Context, attributes: AttributeSet) : super(context, attributes) {
        LayoutInflater.from(context).inflate(R.layout.view_custom_relativelayout, this)
        tvName = find(R.id.tv_name)
        evContent = find(R.id.ev_content)
        isRequired = find(R.id.red_star)
        isRightImg = find(R.id.right_arrow)
        isRightUnit = find(R.id.tv_unit)

        val typedArray = context.obtainStyledAttributes(attributes, R.styleable.CustomRelativeLayout)
        mTvName = typedArray.getString(R.styleable.CustomRelativeLayout_tv_name)
        mEvContentHint = typedArray.getString(R.styleable.CustomRelativeLayout_ev_content_hint)
        mIsRequired = typedArray.getBoolean(R.styleable.CustomRelativeLayout_is_required,true)
        mIsRightImg = typedArray.getBoolean(R.styleable.CustomRelativeLayout_right_img_visible,false)
        mIsRightUnit = typedArray.getBoolean(R.styleable.CustomRelativeLayout_right_unit_visible,false)
        mEvLines = typedArray.getInteger(R.styleable.CustomRelativeLayout_ev_content_lines,1)
        typedArray.recycle()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        initView()
    }

    private fun initView() {
        tvName.text = mTvName
        evContent.hint = mEvContentHint
        evContent.setLines(mEvLines)
        if(mIsRequired) isRequired.visibility = View.VISIBLE else isRequired.visibility = View.GONE
        if(mIsRightImg) isRightImg.visibility = View.VISIBLE else isRightImg.visibility = View.GONE
        if(mIsRightUnit) isRightUnit.visibility = View.VISIBLE else isRightUnit.visibility = View.GONE
    }

    fun getContent() : String{
        return evContent.text.toString()
    }

    fun setHintContent(content: String) {
        evContent.hint = content
    }
}