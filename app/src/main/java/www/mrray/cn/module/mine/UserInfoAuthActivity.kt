package www.mrray.cn.module.mine

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_user_info_auth.*
import kotlinx.android.synthetic.main.layout_mine_info_gp.*
import org.jetbrains.anko.startActivity
import www.mrray.cn.R
import www.mrray.cn.base.BaseActivity
import www.mrray.cn.module.login.LoginHomeActivity

/**
 * 用户信息认证
 */
class UserInfoAuthActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_user_info_auth
    }

    override fun init(savedInstanceState: Bundle?) {
        super.init(savedInstanceState)
        initListener()
    }

    private fun initListener() {
        user_info_auth_username_txt.setOnClickListener {
            startActivity<UserSettingActivity>()
        }
        title_bar.setLeftImgListener {
            finish()
        }
        user_info_auth_email_txt.setOnClickListener {
            startActivity<UserInfoAuthEmailActivity>()
        }
        user_info_auth_username_txt.setOnClickListener {
            startActivity<UserInfoAuthCardActivity>()
        }
    }
}