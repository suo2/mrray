package www.mrray.cn.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.GridView

/**
 * @author suo
 * @date 2018/10/24
 * @desc:嵌套gridview的显示不全问题
 */
class CustomGridView : GridView {

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

    public override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE shr 2,
                View.MeasureSpec.AT_MOST)
        super.onMeasure(widthMeasureSpec, expandSpec)
    }
}