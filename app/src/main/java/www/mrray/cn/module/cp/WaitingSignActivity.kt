package www.mrray.cn.module.cp

import android.os.Bundle
import android.support.v4.app.Fragment
import kotlinx.android.synthetic.main.activity_todo.*
import www.mrray.cn.R
import www.mrray.cn.base.BaseActivity
import www.mrray.cn.module.todo.adapter.TodoPagerAdapter

/**
 * cp 待签
 */
class WaitingSignActivity : BaseActivity() {
    private val mTitleList: ArrayList<String> = ArrayList()
    private val mFragmentList: ArrayList<Fragment> = ArrayList()
    override fun getLayoutId(): Int {
        return R.layout.activity_waiting_sign
    }

    override fun init(savedInstanceState: Bundle?) {
        super.init(savedInstanceState)
        initData()
        initViewPager()
        initTabLayout()
        title_bar.setLeftImgListener { finish() }
    }

    private fun initViewPager() {
        viewpager.adapter = TodoPagerAdapter(this, mTitleList, mFragmentList, supportFragmentManager)
    }

    private fun initData() {
        mTitleList.add("LP待签")
        mTitleList.add("GP待签")
        mFragmentList.add(LpWaitingSignFragment())
        mFragmentList.add(GpWaitingSignFragment())
    }

    private fun initTabLayout() {
        mTitleList.forEach {
            tab_layout.addTab(tab_layout.newTab().setText(it))
        }
        tab_layout.setupWithViewPager(viewpager)
    }
}