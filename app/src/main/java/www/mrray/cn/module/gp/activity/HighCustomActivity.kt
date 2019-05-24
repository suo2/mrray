package www.mrray.cn.module.gp.activity

import android.os.Bundle

import kotlinx.android.synthetic.main.activity_high_custom.*
import www.mrray.cn.R
import www.mrray.cn.base.BaseActivity
import www.mrray.cn.module.gp.HighCustomerFragment

/**
 *@author suo
 *@date 2018/10/26
 *@desc:
 */
class HighCustomActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_fragment_details
    }

    override fun init(savedInstanceState: Bundle?) {
        super.init(savedInstanceState)
            supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, HighCustomerFragment.newInstance())
                    .commit()
        title_bar.setTitleContentText(getString(R.string.high_customer))
        title_bar.setLeftImgListener { finish() }
    }
}
