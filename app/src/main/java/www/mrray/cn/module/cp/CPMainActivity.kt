package www.mrray.cn.module.cp

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.KeyEvent
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import www.mrray.cn.R
import www.mrray.cn.base.BaseActivity
import www.mrray.cn.module.cp.fragment.CpManageFragment
import www.mrray.cn.module.gp.CpDiscoverFragment
import www.mrray.cn.module.gp.fragment.CpHomeFragment
import www.mrray.cn.module.mine.MineFragment




/**
 *@author suo
 *@date 2018/11/6
 *@desc: CP主界面
 */
class CPMainActivity : BaseActivity() {
    private lateinit var home : MenuItem
    private lateinit var discover : MenuItem
    private lateinit var manage: MenuItem
    private lateinit var mine : MenuItem
    private lateinit var mCpHomeFragment : CpHomeFragment
    private lateinit var mCpDiscoverFragment : CpDiscoverFragment
    private lateinit var mCpManageFragment : CpManageFragment
    private lateinit var mMineFragment :MineFragment

    private lateinit var fragments: Array<Fragment>
    private var lastShowFragment = 0

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        resetToDefaultIcon()
        when (item.itemId) {
            R.id.navigation_home -> {
                if (lastShowFragment != 0) {
                    switchFrament(lastShowFragment, 0)
                    lastShowFragment = 0
                }
                item.setIcon(R.mipmap.maintab_home_select)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_discover -> {
                if (lastShowFragment != 1) {
                    switchFrament(lastShowFragment, 1)
                    lastShowFragment = 1
                }
                item.setIcon(R.mipmap.maintab_discover_select)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_manage -> {
                if (lastShowFragment != 2) {
                    switchFrament(lastShowFragment, 2)
                    lastShowFragment = 2
                }
                item.setIcon(R.mipmap.maintab_manage_select)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_mine -> {
                if (lastShowFragment != 3) {
                    switchFrament(lastShowFragment, 3)
                    lastShowFragment = 3
                }
                item.setIcon(R.mipmap.maintab_mine_select)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun init(savedInstanceState: Bundle?) {
        super.init(savedInstanceState)
        home = navigationView.menu.findItem(R.id.navigation_home)
        discover = navigationView.menu.findItem(R.id.navigation_discover)
        manage = navigationView.menu.findItem(R.id.navigation_manage)
        mine = navigationView.menu.findItem(R.id.navigation_mine)
        initFragment()
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigationView.selectedItemId = navigationView.menu.getItem(lastShowFragment).itemId
    }

    private fun initFragment(){
        mCpHomeFragment = CpHomeFragment.newInstance()
        mCpDiscoverFragment = CpDiscoverFragment.newInstance()
        mCpManageFragment = CpManageFragment.newInstance()
        mMineFragment = MineFragment.newInstance()
        fragments = arrayOf(mCpHomeFragment,mCpDiscoverFragment,mCpManageFragment,mMineFragment)
        supportFragmentManager
                .beginTransaction()
                .add(R.id.container, mCpHomeFragment)
                .show(mCpHomeFragment)
                .commit()
    }

    private fun switchFrament(lastIndex : Int ,index : Int) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.hide(fragments[lastIndex])
        if(!fragments[index].isAdded){
            transaction.add(R.id.container,fragments[index])
        }
        transaction.show(fragments[index])
        transaction.commitAllowingStateLoss()

    }

    /**
     * 重置到默认不选中图片
     */
    private fun resetToDefaultIcon(){
        home.setIcon(R.mipmap.maintab_home)
        discover.setIcon(R.mipmap.maintab_discover)
        manage.setIcon(R.mipmap.maintab_manage)
        mine.setIcon(R.mipmap.maintab_mine)
    }

    private var mExitTime: Long = 0

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis().minus(mExitTime) <= 2000) {
                finish()
            } else {
                mExitTime = System.currentTimeMillis()
                toast("再按一次退出程序")
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}

