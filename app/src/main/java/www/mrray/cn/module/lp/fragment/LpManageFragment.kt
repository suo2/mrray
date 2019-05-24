package www.mrray.cn.module.lp.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_lp_manage.*
import www.mrray.cn.R
import www.mrray.cn.module.gp.adapter.DiscoverPageAdapter

/**
 *@author suo
 *@date 2018/10/16
 *@desc: LP 管理界面
 */
class LpManageFragment : Fragment() {

    private val tabList = ArrayList<String>()

    private val fragments = ArrayList<Fragment>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_lp_manage, container, false)

    companion object {
        fun newInstance() = LpManageFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        tabList.add(getString(R.string.already_invested_funds))
        tabList.add(getString(R.string.investment_fund))
        tabList.add(getString(R.string.already_invested_project))
        tabList.add(getString(R.string.investment_project))
        fragments.add(InvestedFundFragment.newInstance())
        fragments.add(InvestingFundFragment.newInstance())
        fragments.add(InvestedProjectFragment.newInstance())
        fragments.add(InvestingProjectFragment.newInstance())
        /**
         * getSupportFragmentManager() 替换为getChildFragmentManager()
         */
        mViewPager.adapter = DiscoverPageAdapter(childFragmentManager, tabList, fragments)
        mTabLayout.setupWithViewPager(mViewPager)
        mTabLayout.getTabAt(0)!!.select()
    }
}