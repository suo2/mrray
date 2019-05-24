package www.mrray.cn.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.view_left_blue_text_view.view.*
import www.mrray.cn.R

class LeftBlueTextView : FrameLayout {
    var content = ""
        set(value) {
            field = value
            invalidate()
        }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        LayoutInflater.from(context).inflate(R.layout.view_left_blue_text_view, this)
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.LeftBlueTextView)
        content = typeArray.getString(R.styleable.LeftBlueTextView_text_content) ?: ""
        initView()
    }

    private fun initView() {
        text_content.text = content
    }
}