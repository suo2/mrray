package www.mrray.cn.module.register.adapter

import android.content.Context
import android.print.PageRange
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mob.tools.gui.ViewPagerAdapter
import org.jetbrains.anko.find
import www.mrray.cn.R
import www.mrray.cn.module.register.TwoData

class RegisterTwoViewpagerAdapter(private val contentList: ArrayList<TwoData>, context: Context?) : PagerAdapter() {
    private val viewList = ArrayList<View>()

    init {
        contentList.forEach {
            val view = LayoutInflater.from(context).inflate(R.layout.item_register_two_viewpager, null, false)
            viewList.add(view)
            view.find<TextView>(R.id.content).text = it.content
            view.find<TextView>(R.id.register_two_type).text = it.type
        }
    }

    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p0 == p1
    }

    override fun getCount(): Int = contentList.size
    override fun destroyItem(view: ViewGroup, position: Int, `object`: Any) {
        view.removeView(viewList.get(position))
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        container.addView(viewList[position])
        return viewList[position]
    }


}