package www.mrray.cn.module.lp

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.KeyEvent
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import www.mrray.cn.R
import www.mrray.cn.base.BaseActivity
import www.mrray.cn.module.gp.LpDiscoverFragment
import www.mrray.cn.module.gp.fragment.LpHomeFragment
import www.mrray.cn.module.lp.fragment.LpManageFragment
import www.mrray.cn.module.mine.MineFragment
import www.mrray.cn.utils.RxPermissionManager


/**
 *@author suo
 *@date 2018/11/6
 *@desc: Lp主界面
 */
class LPMainActivity : BaseActivity() {
    private lateinit var home : MenuItem
    private lateinit var discover : MenuItem
    private lateinit var manage: MenuItem
    private lateinit var mine : MenuItem
    private lateinit var mLpHomeFragment : LpHomeFragment
    private lateinit var mLpDiscoverFragment : LpDiscoverFragment
    private lateinit var mLpManageFragment : LpManageFragment
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
                    switchFragment(lastShowFragment, 0)
                    lastShowFragment = 0
                }
                item.setIcon(R.mipmap.maintab_home_select)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_discover -> {
                if (lastShowFragment != 1) {
                    switchFragment(lastShowFragment, 1)
                    lastShowFragment = 1
                }
                item.setIcon(R.mipmap.maintab_discover_select)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_manage -> {
                if (lastShowFragment != 2) {
                    switchFragment(lastShowFragment, 2)
                    lastShowFragment = 2
                }
                item.setIcon(R.mipmap.maintab_manage_select)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_mine -> {
                if (lastShowFragment != 3) {
                    switchFragment(lastShowFragment, 3)
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
        RxPermissionManager.getCameraPermission(this, {}, {})
        RxPermissionManager.getWriteAndReadPermission(this, {}, {})

        home = navigationView.menu.findItem(R.id.navigation_home)
        discover = navigationView.menu.findItem(R.id.navigation_discover)
        manage = navigationView.menu.findItem(R.id.navigation_manage)
        mine = navigationView.menu.findItem(R.id.navigation_mine)
        initFragment()
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigationView.selectedItemId = navigationView.menu.getItem(lastShowFragment).itemId
    }

    private fun initFragment(){
        mLpHomeFragment = LpHomeFragment.newInstance()
        mLpDiscoverFragment = LpDiscoverFragment.newInstance()
        mLpManageFragment = LpManageFragment.newInstance()
        mMineFragment = MineFragment.newInstance()
        fragments = arrayOf(mLpHomeFragment,mLpDiscoverFragment,mLpManageFragment,mMineFragment)
        supportFragmentManager
                .beginTransaction()
                .add(R.id.container, mLpHomeFragment)
                .show(mLpHomeFragment)
                .commit()
    }

    private fun switchFragment(lastIndex : Int ,index : Int) {
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

