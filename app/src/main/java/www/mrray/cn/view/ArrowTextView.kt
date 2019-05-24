package www.mrray.cn.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.TextView
import www.mrray.cn.R

/**
 * Created by JKWANG-PC on 2016/11/1.
 * 带下箭头的文本框。
 */

class ArrowTextView : TextView {
    private var radius: Float = 0.toFloat()
    private var arrowWidth: Float = 0.toFloat()
    private var arrowHeight: Float = 0.toFloat()
    private var color: Int = 0

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        ini(context, attrs)
    }

    private fun ini(context: Context, attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ArrowTextView)
        radius = typedArray.getDimension(R.styleable.ArrowTextView_radiuss, 0f)
        arrowWidth = typedArray.getDimension(R.styleable.ArrowTextView_arrowWidth, 0f)
        arrowHeight = typedArray.getDimension(R.styleable.ArrowTextView_arrowHeight, 0f)
        color = typedArray.getColor(R.styleable.ArrowTextView_bg, Color.RED)
        typedArray.recycle()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        ini(context, attrs)
    }


    constructor(context: Context) : super(context) {}

    /**
     * @param radius 矩形四角圆角的半径..........
     */
    fun setRadius(radius: Float) {
        this.radius = radius
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        val paint = Paint()
        paint.color = if (color == 0) Color.RED else color
        paint.isAntiAlias = true
        if (radius == 0f) {
            radius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 5f, resources.displayMetrics)
        }
        if (arrowWidth == 0f) {
            arrowWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 15f, resources.displayMetrics)
        }
        if (arrowHeight == 0f) {
            arrowHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 15f, resources.displayMetrics)
        }
        //带圆角的矩形(下边减去三角形的高度...........)
        val width = width
        val height = height - arrowHeight
        canvas.drawRoundRect(RectF(0f, 0f, getWidth().toFloat(), height), radius, radius, paint)
        //画三角形
        val path = Path()
        path.fillType = Path.FillType.EVEN_ODD
        val xMiddle = width / 2f
        val xLeft = xMiddle - arrowWidth / 2
        val xRight = xMiddle + arrowWidth / 2
        val yBottom = height + arrowHeight
        path.moveTo(xMiddle, yBottom)
        path.lineTo(xLeft, height)
        path.lineTo(xRight, height)
        path.lineTo(xMiddle, yBottom)
        path.close()
        canvas.drawPath(path, paint)
        super.onDraw(canvas)
    }
}
