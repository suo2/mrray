package www.mrray.cn.module.mine

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_userinfo_auth_email.*
import org.jetbrains.anko.toast
import www.mrray.cn.R
import www.mrray.cn.base.BaseActivity

class UserInfoAuthEmailActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_userinfo_auth_email
    }

    override fun init(savedInstanceState: Bundle?) {
        super.init(savedInstanceState)
        title_bar.setLeftImgListener {
            finish()
        }
        auth_email.setOnClickListener {
            toast("功能暂未开放")
        }
    }
}