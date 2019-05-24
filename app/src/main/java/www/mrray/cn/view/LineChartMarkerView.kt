package www.mrray.cn.view

import android.annotation.SuppressLint
import android.content.Context
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.github.mikephil.charting.utils.Utils
import kotlinx.android.synthetic.main.linechart_marker_view.view.*

@SuppressLint("ViewConstructor")
/**
 * user:kun
 * Date:2017/6/7 or 下午1:57
 * email:hekun@gamil.com
 * Desc: 自定义MarKerView
 */
class LineChartMarkerView(context: Context?, layoutResource: Int) : MarkerView(context, layoutResource) {

    @SuppressLint("SetTextI18n")
    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        super.refreshContent(e, highlight)

        if (e != null) {
            tvContent.text = "" + Utils.formatNumber(e.y, 0, true)
        }
    }

    override fun getOffset(): MPPointF {
        return MPPointF((-(width / 2)).toFloat(), (-height).toFloat())
    }
}