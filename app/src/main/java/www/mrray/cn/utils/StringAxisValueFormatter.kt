package www.mrray.cn.utils

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter

/**
 * Created by Charlie on 2016/9/23.
 * 对字符串类型的坐标轴标记进行格式化
 */
class StringAxisValueFormatter
/**
 * 对字符串类型的坐标轴标记进行格式化
 * @param strs
 */
(//区域值
        private val mStrs: List<String>?) : IAxisValueFormatter {

    override fun getFormattedValue(v: Float, axisBase: AxisBase): String {
        val index = v.toInt()
        return if (mStrs == null || mStrs.isEmpty() || index < 0 || index > mStrs.size - 1) {
            ""
        } else mStrs[index]
    }
}
