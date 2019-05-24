package www.mrray.cn.module.gp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import kotlinx.android.synthetic.main.fragment_gp_discover.*
import www.mrray.cn.R
import www.mrray.cn.base.BaseViewModelFragment
import www.mrray.cn.module.gp.adapter.DiscoverPageAdapter
import www.mrray.cn.repository.gp.GpManageRepository
import www.mrray.cn.viewmodel.gp.GpManageViewModel

/**
 *@author suo
 *@date 2018/10/16
 *@desc: GP 管理界面
 */
class GpManageFragment : BaseViewModelFragment<GpManageViewModel,GpManageRepository>() {

    private val tabList = ArrayList<String>()

    private val fragments = ArrayList<Fragment>()

    companion object {
        fun newInstance() = GpManageFragment()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_gp_manage
    }

    override fun init(view: View, savedInstanceState: Bundle?) {
        super.init(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        tabList.add(getString(R.string.funds_manage))
        tabList.add(getString(R.string.lp_manage))
        fragments.add(FundsManageFragment.newInstance())
        fragments.add(LpManageListFragment.newInstance())
        /**
         * getSupportFragmentManager() 替换为getChildFragmentManager()
         */
        mViewPager.adapter = DiscoverPageAdapter(childFragmentManager, tabList, fragments)
        mTabLayout.setupWithViewPager(mViewPager)
    }
}