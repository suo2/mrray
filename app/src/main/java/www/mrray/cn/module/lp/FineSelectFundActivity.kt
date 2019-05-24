package www.mrray.cn.module.lp

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_fragment_details.*
import www.mrray.cn.R
import www.mrray.cn.base.BaseActivity
import www.mrray.cn.module.lp.fragment.FineSelectFundInfoFragment

/**
 *@author suo
 *@date 2018/10/26
 *@desc:
 */
class FineSelectFundActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_fragment_details
    }

    override fun init(savedInstanceState: Bundle?) {
        super.init(savedInstanceState)
            supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, FineSelectFundInfoFragment.newInstance())
                    .commit()
        title_bar.setTitleContentText(getString(R.string.fine_selectfund))
        title_bar.setLeftImgListener { finish() }
    }
}
