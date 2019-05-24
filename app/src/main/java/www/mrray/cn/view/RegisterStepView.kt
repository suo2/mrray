package www.mrray.cn.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.view_register_step.view.*
import www.mrray.cn.R

/**
 * 注册页面view
 */
class RegisterStepView : FrameLayout {

    constructor(context: Context) : super(context) {

    }

    var leftDividerVisible: Boolean = false
        set(value) {
            field = value
            register_view_step_left.visibility = if (value) View.VISIBLE else View.INVISIBLE
        }
    var leftDividerColor: Int = R.color.register_step_undone_color
        set(value) {
            field = value
            register_view_step_left.setBackgroundColor(value)
        }

    var rightDividerVisible: Boolean = false
        set(value) {
            field = value
            register_view_step_right.visibility = if (value) View.VISIBLE else View.INVISIBLE
        }
    var rightDividerColor: Int = R.color.register_step_undone_color
        set(value) {
            field = value
            register_view_step_right.setBackgroundColor(value)
        }

    private var stepName: String = "账号密码"
    var stepNameColor: Int = R.color.register_step_undone_color
        set(value) {
            field = value
            register_txt_step.setTextColor(value)
        }
    private var stepNum: String = "1"
    var stepNumColor: Int = R.color.register_step_undone_color
        set(value) {
            field = value
            register_img_step.setTextColor(value)
        }
    var stepNumBackGround: Int = R.drawable.register_step_num_background_undone
        set(value) {
            field = value
            register_img_step.setBackgroundResource(value)
        }

    @SuppressLint("ResourceAsColor")
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        LayoutInflater.from(context).inflate(R.layout.view_register_step, this)

        val typeArray = context.obtainStyledAttributes(attributeSet, R.styleable.RegisterStepView)
        leftDividerVisible = typeArray.getBoolean(R.styleable.RegisterStepView_left_divider_visible, true)
        leftDividerColor = typeArray.getColor(R.styleable.RegisterStepView_left_divider_color, R.color.register_step_undone_color)

        rightDividerVisible = typeArray.getBoolean(R.styleable.RegisterStepView_right_divider_visible, true)
        rightDividerColor = typeArray.getColor(R.styleable.RegisterStepView_right_divider_color, R.color.register_step_undone_color)

        stepName = typeArray.getString(R.styleable.RegisterStepView_current_step_name) ?: ""
        stepNameColor = typeArray.getColor(R.styleable.RegisterStepView_current_step_name_color, R.color.register_step_undone_color)

        stepNum = typeArray.getString(R.styleable.RegisterStepView_current_step_num) ?: ""
        stepNumColor = typeArray.getColor(R.styleable.RegisterStepView_current_step_num_color, R.color.register_step_undone_color)
        stepNumBackGround = typeArray.getResourceId(R.styleable.RegisterStepView_current_step_num_background, R.drawable.register_step_num_background_undone)

        initView()
    }

    @SuppressLint("ResourceAsColor")
    private fun initView() {
        register_img_step.text = stepNum
        register_img_step.setTextColor(stepNumColor)
        register_img_step.setBackgroundResource(stepNumBackGround)

        register_txt_step.text = stepName
        register_txt_step.setTextColor(stepNameColor)

        register_view_step_left.visibility = if (leftDividerVisible) View.VISIBLE else View.INVISIBLE
        register_view_step_left.setBackgroundColor(leftDividerColor)

        register_view_step_right.visibility = if (rightDividerVisible) View.VISIBLE else View.INVISIBLE
        register_view_step_right.setBackgroundColor(rightDividerColor)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

}