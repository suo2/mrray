package www.mrray.cn.module.gp.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import java.util.*

/**
 * @author suo
 * @date 2018/10/17
 * @desc: 适配器
 */
class DiscoverPageAdapter(fm: FragmentManager, private val titleList: ArrayList<String>, private val fragmentList: ArrayList<Fragment>) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }
}