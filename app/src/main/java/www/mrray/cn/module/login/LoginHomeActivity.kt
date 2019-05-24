package www.mrray.cn.module.login

import android.content.Intent
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import www.mrray.cn.R
import www.mrray.cn.base.BaseActivity
import www.mrray.cn.module.cp.CPMainActivity
import www.mrray.cn.module.gp.GPMainActivity
import www.mrray.cn.module.lp.LPMainActivity

/**
 * 登录主页面
 */
class LoginHomeActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_login_home
    }

    override fun init(savedInstanceState: Bundle?) {
        super.init(savedInstanceState)
        if (mDataCatch.getLoginModel() != null) {
            mDataCatch.switchUserIdentity({
                intent = Intent(this, GPMainActivity::class.java)
            }, {
                intent = Intent(this, LPMainActivity::class.java)
            }, {
                intent = Intent(this, CPMainActivity::class.java)
            }, {}, {})
            startActivity(intent)
            this.finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val fragment = supportFragmentManager.findFragmentById(R.id.login_fragment)
        return NavHostFragment.findNavController(fragment!!).navigateUp()
    }
}