package www.mrray.cn.module.mine

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_user_setting.*
import org.jetbrains.anko.startActivity
import www.mrray.cn.R
import www.mrray.cn.base.BaseActivity
import www.mrray.cn.base.BaseViewModelActivity
import www.mrray.cn.repository.mine.MineRepository
import www.mrray.cn.viewmodel.mine.MineViewModel

/**
 * 账户设置
 */
class UserSettingActivity : BaseViewModelActivity<MineViewModel, MineRepository>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_user_setting
    }

    override fun init(savedInstanceState: Bundle?) {
        super.init(savedInstanceState)
        user_setting_face_auth_login.setOnClickListener {
            startActivity<UserInfoAuthFaceActivity>()
        }
        title_bar.setLeftImgListener { finish() }
    }

    override fun onResume() {
        super.onResume()
        if (user_setting_face_auth_login != null) {
            user_setting_face_auth_login.setDesContent(if (mDataCatch.getOpenFaceAUth()) "已设置" else "未设置")
        }
    }

}