package www.mrray.cn.module.register

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View
import kotlinx.android.synthetic.main.fragment_register_two.*
import www.mrray.cn.R
import www.mrray.cn.base.BaseViewModelFragment
import www.mrray.cn.module.register.adapter.RegisterTwoViewpagerAdapter
import www.mrray.cn.repository.register.RegisterRepository
import www.mrray.cn.viewmodel.register.RegisterViewModel

/**
 * 第二步
 */
class RegisterTwoFragment : BaseViewModelFragment<RegisterViewModel, RegisterRepository>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_register_two
    }

    var mCurrentPage: Int = 3

    override fun init(view: View, savedInstanceState: Bundle?) {
        super.init(view, savedInstanceState)
        setViewpager()
    }

    /**
     * 由于所有的数据最后一次提交上去 所以前面只做校验
     */
    fun nextStep(success: (Int) -> Unit) {
        success(mCurrentPage)
    }

    val contentList = ArrayList<TwoData>()


    private fun setViewpager() {
        contentList.add(TwoData(3, "普通合伙人（GeneralParther）：泛 指股权投资基金的管理机构或自然人, 英文简称GP。普通合伙人对合伙企业 债务承担无限连带责任，有限合伙 人 以其认缴的出资额为限对合伙企业 债 务承担责任", "GP"))
        contentList.add(TwoData(1, "有限合伙人，即参与投资的企业或金融保险机构等机构投资人和个人投资人，或经其他合伙人一致同 意依法转为有限合伙人的，被依法 认定为无民事行为能力人或者限制 民事行为能力人的合伙人。这些人 只承担有限责任。", "LP"))
        contentList.add(TwoData(4, "普通合伙人（GeneralParther）：泛指股权投资基金的管理机构或自然人, 英文简称GP。普通合伙人对合伙企业 债务承担无限连带责任，有限合伙 人 以其认缴的出资额为限对合伙企业 债 务承担责任", "CP"))
        /*    contentList.add(TwoData(5, "普通合伙人（GeneralParther）：泛\n" +
                    "指股权投资基金的管理机构或自然人,\n" +
                    "英文简称GP。普通合伙人对合伙企业\n" +
                    "债务承担无限连带责任，有限合伙 人\n" +
                    "以其认缴的出资额为限对合伙企业 债\n" +
                    "务承担责任。\n" +
                    "\n", "FA"))*/
        register_step_viewpager.adapter = RegisterTwoViewpagerAdapter(contentList, context)
        register_step_viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {
            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            override fun onPageSelected(p0: Int) {
                mCurrentPage = contentList[p0].page
            }
        })
    }
}

data class TwoData(var page: Int, var content: String, var type: String)