package www.mrray.cn.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import org.jetbrains.anko.find
import www.mrray.cn.R

/**
 * 顶部titlebar
 */
class TitleBar : FrameLayout {

    private var mRightVisible: Int = View.GONE
    private var mLeftVisible: Int = View.VISIBLE
    private var mTitleContent: String = ""
    private var mLeftIconId: Int = 0
    private var mRightIconId: Int = 0

    private lateinit var mRightImg: ImageView
    private lateinit var mLeftImg: ImageView
    public lateinit var mTitleText: TextView

    constructor(context: Context) : super(context)
    constructor(context: Context, attributes: AttributeSet) : super(context, attributes) {
        LayoutInflater.from(context).inflate(R.layout.view_title_bar, this)
        mRightImg = find(R.id.right_btn)
        mLeftImg = find(R.id.left_btn)
        mTitleText = find(R.id.title_name_text)

        val typedArray = context.obtainStyledAttributes(attributes, R.styleable.TitleBar)
        mRightVisible = if (typedArray.getBoolean(R.styleable.TitleBar_right_visible, false)) View.VISIBLE else View.GONE
        mLeftVisible = if (typedArray.getBoolean(R.styleable.TitleBar_left_visible, true)) View.VISIBLE else View.GONE
        mTitleContent = typedArray.getString(R.styleable.TitleBar_title_text) ?: ""
        mRightIconId = typedArray.getResourceId(R.styleable.TitleBar_right_icon, R.mipmap.app_icon)
        mLeftIconId = typedArray.getResourceId(R.styleable.TitleBar_left_icon, R.mipmap.back_white)
        typedArray.recycle()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        initView()
    }

    fun setRightImgListener(callback: (View) -> Unit) {
        mRightImg.setOnClickListener {
            callback(it)
        }
    }

    fun setLeftImgListener(callback: (View) -> Unit) {
        mLeftImg.setOnClickListener {
            callback(it)
        }
    }

    fun setTitleContentText(content: String) {
        mTitleText.text = content
    }

    fun setLeftIcon(id: Int) {
        mLeftImg.setImageResource(id)
    }

    fun setRightIcon(id: Int) {
        mRightImg.setImageResource(id)
    }

    private fun initView() {
        mRightImg.visibility = mRightVisible
        mLeftImg.visibility = mLeftVisible
        mTitleText.text = mTitleContent
        mRightImg.setImageResource(mRightIconId)
        mLeftImg.setImageResource(mLeftIconId)
    }
}