package www.mrray.cn.module.mine

import android.annotation.SuppressLint
import www.mrray.cn.R
import www.mrray.cn.base.BaseViewModelActivity
import www.mrray.cn.repository.mine.MineRepository
import www.mrray.cn.viewmodel.mine.MineViewModel

@SuppressLint("Registered")
/**
 * 用户认证
 */
class NameVerificationActivity : BaseViewModelActivity<MineViewModel, MineRepository>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_user_setting
    }

}