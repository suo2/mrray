package www.mrray.cn.module.gp.activity

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_release_fund.*
import org.jetbrains.anko.startActivity
import www.mrray.cn.R
import www.mrray.cn.base.BaseActivity

/**
 *@author suo
 *@date 2018/10/19
 *@desc: 发布基金
 */
class ReleaseFundAvtivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_release_fund
    }

    override fun init(savedInstanceState: Bundle?) {
        super.init(savedInstanceState)
        fundIntro.setOnClickListener{
            startActivity<FundIntroActivity>()
        }
    }
}