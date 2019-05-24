package www.mrray.cn.utils

import android.graphics.Color
import android.os.Build
import android.support.v4.content.ContextCompat
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import www.mrray.cn.R
import www.mrray.cn.view.LineChartMarkerView
import java.util.*

/**
 *@author suo
 *@date 2018/11/5
 *@desc: MPChart辅助类
 */
object MPChartHelper {

    val xLineColor :Int = Color.parseColor("#d2d2d2")
    val redLine : Int = Color.parseColor("#f96d3d")

    /**
     * 单线单y轴图。
     *
     * @param lineChart
     * @param xAxisValue
     * @param yAxisValue
     * @param title
     * @param showSetValues 是否在折线上显示数据集的值。true为显示，此时y轴上的数值不可见，否则相反。
     */
    fun setLineChart(mLineChart: LineChart, xAxisValue: List<String>, yAxisValue: List<Float>, title: String, isFirst: Boolean) {
//        mLineChart.xAxis.setLabelCount(7, true)
        initChart(mLineChart,xAxisValue,yAxisValue ,isFirst)

        val values = ArrayList<Entry>()
        var set: LineDataSet? = null
        for(i: Int in  xAxisValue.indices){
            values.add(Entry(i.toFloat(),yAxisValue[i]))
        }
        if (mLineChart.data != null &&
                mLineChart.data.dataSetCount > 0) {
            set = mLineChart.data.getDataSetByIndex(0) as LineDataSet?
            set!!.values = values
            mLineChart.data.notifyDataChanged()
            mLineChart.notifyDataSetChanged()
        } else {
            set = LineDataSet(values, title)
            set.setCircleColor(redLine)
            set.setCircleColorHole(Color.WHITE)
            set.circleHoleRadius = 2f
            set.color = redLine
            set.lineWidth = 2f
            set.circleRadius = 3f
            //将其设置为true可在每个数据圈中绘制一个圆
            set.setDrawCircleHole(true)
            set.valueTextSize = 9f
            //是否填充，默认false
            set.setDrawFilled(true)
            set.fillColor = redLine
            //设置背景渐变
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
                //设置渐变
                set.fillDrawable = ContextCompat.getDrawable(mLineChart.context,R.drawable.line_chart_bg)
            }else {
                set.fillColor = redLine
            }
            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(set)
            mLineChart.data = LineData(dataSets)
        }
        mLineChart.animateXY(1500,1500, Easing.EasingOption.EaseInSine, Easing.EasingOption.EaseInSine)
    }

    /**
     * 初始化
     *
     * @param mLineChart
     * @param xAxisValue
     */
    private fun initChart(mLineChart: LineChart ,xAxisValue: List<String> ,yXAxisValues: List<Float>,isFirst :Boolean) {
        mLineChart.isAutoScaleMinMaxEnabled = false
        mLineChart.description.isEnabled = false
        //设置marker
//        val myMarkerView = LineChartMarkerView(mLineChart.context, R.layout.linechart_marker_view)
//        myMarkerView.chartView=mLineChart
//        mLineChart.marker=myMarkerView
        var yValaueMax = 0f
        var yValaueMin = 0f

        //y轴设置
        val leftAxis = mLineChart.axisLeft
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        leftAxis.setDrawGridLines(false)

        //获得y轴的最大值和最小值
        if (yXAxisValues.isNotEmpty()) {
            yValaueMax = Collections.max(yXAxisValues)
            yValaueMin = Collections.min(yXAxisValues)
        }
        //根据y轴的最大值和最小值来设置y轴
        if (yValaueMin < 0f) {
            var num = 0
            var leftMax = yValaueMax.toInt() + 1
            var leftMin = yValaueMin.toInt() - 1
            while (leftMax % 5 != 0) {
                leftMax++
            }
            while (Math.abs(leftMin) % 5 != 0) {
                leftMin--
            }
            val itemMax = leftMax / 5
            val itemMin = Math.abs(leftMin / 5)
            val item = if (itemMax > itemMin) itemMax else itemMin
            while (leftMax % item != 0) {
                leftMax++
            }
            while (Math.abs(leftMin) % item != 0) {
                leftMin--
            }
            num = leftMax / item - leftMin / item
            leftAxis.axisMaximum = leftMax.toFloat()
            leftAxis.axisMinimum = leftMin.toFloat()
            leftAxis.setLabelCount(num + 1, true)
        } else {
            if (yValaueMax != 0f) {
                //解决不等分的问题单
                var leftMax = yValaueMax.toInt() + 1
                while (leftMax % 5 != 0) {
                    leftMax++
                }
                leftAxis.axisMaximum = leftMax.toFloat()
            } else {
                leftAxis.axisMaximum = 1f
            }
            leftAxis.axisMinimum = 0f
            leftAxis.setLabelCount(6, true)
        }

        //x轴
        //自定义设置横坐标
        var xValueFormatter: IAxisValueFormatter = StringAxisValueFormatter(xAxisValue)
        //X轴
        var xAxis = mLineChart.xAxis
        //设置线为虚线
        //xAxis.enableGridDashedLine(10f, 10f, 0f);
        //设置字体大小10sp
        xAxis.textSize = 10f
        //设置X轴字体颜色
        xAxis.textColor = xLineColor
        //设置从X轴发出横线
        xAxis.setDrawGridLines(true)
        xAxis.gridColor = xLineColor
        //设置网格线宽度
        xAxis.gridLineWidth = 1f
        //设置显示X轴
        xAxis.setDrawAxisLine(true)
        //设置X轴显示的位置
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        //设置自定义X轴值
        xAxis.valueFormatter = xValueFormatter
        //一个界面显示6个Lable，那么这里要设置11个

        if(xAxisValue.size>=6){
            xAxis.labelCount = 5
        }else{
            xAxis.labelCount = xAxisValue.size - 1
        }
        //设置最小间隔，防止当放大时出现重复标签
        xAxis.granularity = 1f
        //设置为true当一个页面显示条目过多，X轴值隔一个显示一个
        xAxis.isGranularityEnabled = false
        //设置X轴的颜色
        xAxis.axisLineColor = xLineColor
        //设置X轴的宽度
        xAxis.axisLineWidth = 1f

        //设置从Y轴发出横向直线(网格线)
        leftAxis.setDrawGridLines(true)
        //设置网格线的颜色
        leftAxis.gridColor = xLineColor
        //设置网格线宽度
        leftAxis.gridLineWidth = 1f
        //设置Y轴最小值是0，从0开始
        leftAxis.axisMinimum = 0f
        leftAxis.isGranularityEnabled = true
        //设置最小间隔，防止当放大时出现重复标签
        leftAxis.granularity = 1f
        //如果沿着轴线的线应该被绘制，则将其设置为true,隐藏Y轴
        leftAxis.setDrawAxisLine(false)
        leftAxis.setDrawZeroLine(false)
        leftAxis.textSize = 10f
        leftAxis.textColor = xLineColor
        //设置左边X轴显示
        leftAxis.isEnabled = true
        //设置Y轴的颜色
        leftAxis.axisLineColor = xLineColor
        //设置Y轴的宽度
        leftAxis.axisLineWidth = 1f

        val rightAxis = mLineChart.axisRight
        //设置右边Y轴不显示
        rightAxis.isEnabled = false


        val legend : Legend = mLineChart.legend
        legend.isEnabled = false
//        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
//        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
//        legend.orientation = Legend.LegendOrientation.HORIZONTAL
//        legend.setDrawInside(true)
//        legend.direction = Legend.LegendDirection.LEFT_TO_RIGHT
//        legend.form = Legend.LegendForm.LINE
//        legend.textSize = 10f


        //设置是否可以触摸
        mLineChart.setTouchEnabled(true)
        mLineChart.dragDecelerationFrictionCoef = 0.9f
        //设置是否可以拖拽
        mLineChart.isDragEnabled = true
        //设置是否可以缩放
        mLineChart.setScaleEnabled(false)
        mLineChart.setDrawGridBackground(false)
        mLineChart.isHighlightPerDragEnabled = true
        mLineChart.setPinchZoom(true)
        //设置背景颜色
        mLineChart.setBackgroundColor(Color.WHITE)
        //设置一页最大显示个数为6，超出部分就滑动
        var ratio : Float =   xAxisValue.size / 6f
        //显示的时候是按照多大的比率缩放显示,1f表示不放大缩小
        if(isFirst) mLineChart.zoom(ratio,1f,0f,0f)
    }


    /**
     * 设置双柱状图样式
     *
     * @param barChart
     * @param xAxisValue
     * @param yAxisValue1
     * @param yAxisValue2
     * @param bartilte1
     * @param bartitle2
     */
    fun setTwoBarChart(barChart: BarChart, xAxisValue: List<String>, yAxisValue1: List<Float>, yAxisValue2: List<Float>, bartilte1: String, bartitle2: String) {

        //设置是否可以拖拽
        barChart.isDragEnabled = true
        //设置是否可以缩放
        barChart.setTouchEnabled(true)
        barChart.setScaleEnabled(false)
        barChart.setDrawGridBackground(false)
        barChart.description.isEnabled = false//设置描述
        barChart.setPinchZoom(true)//设置按比例放缩柱状图
        barChart.extraBottomOffset = 10f
        barChart.extraTopOffset = 20f
        barChart.highlightValues(null)

        //x坐标轴设置
        val xAxis = barChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(true)
        xAxis.gridColor = xLineColor
        xAxis.granularity = 1f
        xAxis.valueFormatter = StringAxisValueFormatter(xAxisValue)
        xAxis.labelCount = xAxisValue.size - 1
        //设置为true当一个页面显示条目过多，X轴值隔一个显示一个
        xAxis.isGranularityEnabled = false
        xAxis.textColor = xLineColor
        xAxis.setCenterAxisLabels(true)//设置标签居中
        xAxis.labelRotationAngle = -30f//设置x轴字体显示角度
        //点击显示值
        val myMarkerView = LineChartMarkerView(barChart.context, R.layout.linechart_marker_view)
        myMarkerView.chartView=barChart
        barChart.marker = myMarkerView
        //y轴设置
        val leftAxis = barChart.axisLeft
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)//设置y轴标签显示在外侧
        leftAxis.axisMinimum = 0f//设置Y轴最小值
        leftAxis.setDrawGridLines(true)
        leftAxis.gridColor = xLineColor
        leftAxis.textColor = xLineColor
        leftAxis.setDrawLabels(true)//禁止绘制y轴标签
        leftAxis.setDrawAxisLine(true)//禁止绘制y轴
        barChart.axisRight.isEnabled = false//禁用右侧y轴
        //设置坐标轴最大最小值
        val yMin1 = Collections.min(yAxisValue1)
        val yMin2 = Collections.min(yAxisValue2)
        val yMax1 = Collections.max(yAxisValue1)
        val yMax2 = Collections.max(yAxisValue2)
        val yMin = java.lang.Double.valueOf((if (yMin1 < yMin2) yMin1 else yMin2) * 0.1).toFloat()
        val yMax = java.lang.Double.valueOf((if (yMax1 > yMax2) yMax1 else yMax2) * 1.1).toFloat()

        if (yMax == 0f || yMax == java.lang.Float.MIN_VALUE) {
            leftAxis.axisMaximum = 1f
            leftAxis.setLabelCount(6, true)
        } else {
            leftAxis.axisMaximum = java.lang.Double.valueOf(yMax * 1.1).toFloat()
        }
        leftAxis.axisMinimum = java.lang.Double.valueOf(yMin * 0.1).toFloat()

        barChart.axisRight.isEnabled = false


        //图例设置
        val legend = barChart.legend
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        legend.setDrawInside(true)
        legend.direction = Legend.LegendDirection.LEFT_TO_RIGHT
        legend.form = Legend.LegendForm.SQUARE
        legend.textSize = 10f

        //设置柱状图数据
        setTwoBarChartData(barChart, xAxisValue, yAxisValue1, yAxisValue2, bartilte1, bartitle2)

        barChart.animateXY(1500,1500, Easing.EasingOption.EaseInSine, Easing.EasingOption.EaseInSine)
    }


    /**
     * 设置柱状图数据源
     * 电站对比
     */
    private fun setTwoBarChartData(barChart: BarChart, xAxisValue: List<String>, yAxisValue1: List<Float>, yAxisValue2: List<Float>, bartilte1: String, bartitle2: String) {
        val groupSpace = 0.16f
        val barSpace = 0.02f
        val barWidth = 0.4f

//        val groupSpace = 20f
//        val barSpace = 3f
//        val barWidth = 40f
        // (0.45 + 0.03) * 2 + 0.04 = 1，即一个间隔为一组，包含两个柱图 -> interval per "group"

        val entries1 = ArrayList<BarEntry>()
        val entries2 = ArrayList<BarEntry>()

        var i = 0
        val n = yAxisValue1.size
        while (i < n) {
            entries1.add(BarEntry(i.toFloat(), yAxisValue1[i]))
            entries2.add(BarEntry(i.toFloat(), yAxisValue2[i]))
            ++i
        }
        val dataset1: BarDataSet
        val dataset2: BarDataSet

        if (barChart.data != null && barChart.data.dataSetCount > 0) {
            dataset1 = barChart.data.getDataSetByIndex(0) as BarDataSet
            dataset2 = barChart.data.getDataSetByIndex(1) as BarDataSet
            dataset1.values = entries1
            dataset2.values = entries2
            barChart.data.notifyDataChanged()
            barChart.notifyDataSetChanged()
        } else {
            dataset1 = BarDataSet(entries1, bartilte1)
            dataset2 = BarDataSet(entries2, bartitle2)
            dataset1.color = Color.parseColor("#ffc63d")
            dataset2.color = Color.parseColor("#2388d8")

            val dataSets = ArrayList<IBarDataSet>()
            dataset1.setDrawValues(false)
            dataset2.setDrawValues(false)
            dataSets.add(dataset1)
            dataSets.add(dataset2)

            val data = BarData(dataSets)
            data.setValueTextSize(10f)
            data.barWidth = 0.9f
            data.setValueFormatter { value, entry, i, viewPortHandler -> Utils.round(value.toDouble(), 2) }
            barChart.data = data
        }

//        barChart.barData.barWidth = barWidth
//        barChart.xAxis.axisMinimum = xAxisValue[0].toFloat()
       //  barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
//        barChart.xAxis.axisMaximum = barChart.barData.getGroupWidth(groupSpace, barSpace) * xAxisValue.size
//        barChart.groupBars(xAxisValue[0], groupSpace, barSpace)

//        val barAmount : Int = xAxisValue.size//需要显示柱状图的类别 数量
//        //设置组间距占比30% 每条柱状图宽度占比 70% /barAmount  柱状图间距占比 0%
//        val groupSpace  = 0.3f //柱状图组之间的间距
//        val barWidth:Float = (1f - groupSpace) / barAmount
//        val barSpace :Float= 0f
        //设置柱状图宽度
        barChart.barData.barWidth = barWidth
        //(起始点、柱状图组间距、柱状图之间间距)
        barChart.barData.groupBars((0-0.5f), groupSpace, barSpace)
    }
}