package www.mrray.cn.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import org.jetbrains.anko.find
import www.mrray.cn.R

/**
 * 用户信息认证页面item
 */
class UserInfoAuthItemView : FrameLayout {

    constructor(context: Context) : super(context)

    private var mLeftId: Int = R.mipmap.app_icon

    private var mRightId: Int = R.mipmap.app_icon

    private var mTitleContent: String = ""

    private var mDesContent: String = ""

    constructor(context: Context, attributes: AttributeSet) : super(context, attributes) {
        LayoutInflater.from(context).inflate(R.layout.view_user_info_auth, this, true)
        val typedArray = context.obtainStyledAttributes(attributes, R.styleable.UserInfoAuthItemView)
        mLeftId = typedArray.getResourceId(R.styleable.UserInfoAuthItemView_left_img, R.mipmap.app_icon)
        mRightId = typedArray.getResourceId(R.styleable.UserInfoAuthItemView_right_img, R.mipmap.right_arrow)
        mTitleContent = typedArray.getString(R.styleable.UserInfoAuthItemView_title_content)?:""
        mDesContent = typedArray.getString(R.styleable.UserInfoAuthItemView_des_content)?:""
        initView()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    private fun initView() {
        val leftImg = find<ImageView>(R.id.user_info_auth_left_img)
        val rightImg = find<ImageView>(R.id.user_info_auth_right_img)
        val titleContent = find<TextView>(R.id.user_info_auth_title_content)
        val desContent = find<TextView>(R.id.user_info_auth_des_content)

        leftImg.setImageResource(mLeftId)
        rightImg.setImageResource(mRightId)
        titleContent.text = mTitleContent
        desContent.text = mDesContent
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        initView()
    }
}