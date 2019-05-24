package www.mrray.cn.module.mine

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_userinfo_auth_card.*
import org.jetbrains.anko.toast
import www.mrray.cn.R
import www.mrray.cn.base.BaseActivity

class UserInfoAuthCardActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_userinfo_auth_card
    }

    override fun init(savedInstanceState: Bundle?) {
        super.init(savedInstanceState)
        title_bar.setLeftImgListener { finish() }
        user_auth.setOnClickListener {
            toast("功能暂未开放")
        }
    }

}