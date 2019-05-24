package www.mrray.cn.module.gp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import kotlinx.android.synthetic.main.fragment_gp_discover.*
import www.mrray.cn.R
import www.mrray.cn.base.BaseViewModelFragment
import www.mrray.cn.module.gp.adapter.DiscoverPageAdapter
import www.mrray.cn.module.lp.fragment.FineSelectFundInfoFragment
import www.mrray.cn.repository.gp.GpDiscoverRepository
import www.mrray.cn.viewmodel.gp.GpDiscoverViewModel

/**
 *@author suo
 *@date 2018/11/6
 *@desc: CP 发现
 */
class CpDiscoverFragment : BaseViewModelFragment<GpDiscoverViewModel,GpDiscoverRepository>() {

    private val tabList = ArrayList<String>()

    private val fragments = ArrayList<Fragment>()

    companion object {
        fun newInstance() = CpDiscoverFragment()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_lp_discover
    }

    override fun init(view: View, savedInstanceState: Bundle?) {
        super.init(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        tabList.add(getString(R.string.fine_selectfund))
        tabList.add(getString(R.string.high_customer))
        fragments.add(FineSelectFundInfoFragment.newInstance())
        fragments.add(HighCustomerFragment.newInstance())
        /**
         * getSupportFragmentManager() 替换为getChildFragmentManager()
         */
        mViewPager.adapter = DiscoverPageAdapter(childFragmentManager, tabList, fragments)
        mTabLayout.setupWithViewPager(mViewPager)
    }
}