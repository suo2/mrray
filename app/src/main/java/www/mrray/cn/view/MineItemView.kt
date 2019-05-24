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
 * 我的页面item
 */
class MineItemView : FrameLayout {

    constructor(context: Context) : super(context)

    private var mLeftId: Int = R.mipmap.app_icon

    private var mRightId: Int = R.mipmap.app_icon

    private var mTitleContent: String = ""

    var mDesContent: String = ""

    fun setDesContent(content: String) {
        desContent.text = content
    }

    constructor(context: Context, attributes: AttributeSet) : super(context, attributes) {
        LayoutInflater.from(context).inflate(R.layout.view_mine, this, true)
        val typedArray = context.obtainStyledAttributes(attributes, R.styleable.MineItemView)
        mLeftId = typedArray.getResourceId(R.styleable.MineItemView_mine_left_img, R.mipmap.message_notify)
        mRightId = typedArray.getResourceId(R.styleable.MineItemView_mine_right_img, R.mipmap.right_arrow)
        mTitleContent = typedArray.getString(R.styleable.MineItemView_mine_title_content) ?: ""
        mDesContent = typedArray.getString(R.styleable.MineItemView_mine_des_content) ?: ""
        initView()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    private lateinit var desContent: TextView

    private fun initView() {
        val leftImg = find<ImageView>(R.id.mine_left_img)
        val rightImg = find<ImageView>(R.id.mine_right_img)
        val titleContent = find<TextView>(R.id.mine_title_content)
        desContent = find(R.id.mine_des_content)

        leftImg.setImageResource(mLeftId)
        rightImg.setImageResource(mRightId)
        if (mDesContent.isEmpty()) desContent.visibility = View.GONE
        titleContent.text = mTitleContent
        desContent.text = mDesContent
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        initView()
    }
}