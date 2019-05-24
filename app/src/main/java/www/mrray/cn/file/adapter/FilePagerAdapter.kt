package www.mrray.cn.file.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class FilePagerAdapter(val context: Context, val titles: ArrayList<String>, val fragments: ArrayList<Fragment>, fragmentManager: FragmentManager)
    : FragmentPagerAdapter(fragmentManager) {
    override fun getItem(p0: Int): Fragment = fragments[p0]

    override fun getCount(): Int = titles.size

    override fun getPageTitle(position: Int): CharSequence? = titles[position]
}