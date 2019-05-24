package www.mrray.cn.module.cp

import android.os.Bundle
import android.view.View
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.fragment_cp_manager_home.*
import me.jessyan.autosize.internal.CancelAdapt
import www.mrray.cn.R
import www.mrray.cn.base.BaseFragment

class CpManagerHomeFragment : BaseFragment(), CancelAdapt {

    companion object {
        fun newInstance() = CpManagerHomeFragment()
    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_cp_manager_home
    }

    override fun init(view: View, savedInstanceState: Bundle?) {
        super.init(view, savedInstanceState)
        lineChart.axisRight.isEnabled = false
        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        //显示边界
        lineChart.setDrawBorders(true)
        lineChart.xAxis

        //设置数据
        val entries = ArrayList<Entry>()
        for (i in 0..10) {
            entries.add(Entry(i.toFloat() + 0.566f, ((Math.random()) * 80).toFloat()))
        }
        //一个LineDataSet就是一条线
        val lineDataSet = LineDataSet(entries, "温度")
        lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER

        val data = LineData(lineDataSet)
        lineChart.setData(data)
    }
}