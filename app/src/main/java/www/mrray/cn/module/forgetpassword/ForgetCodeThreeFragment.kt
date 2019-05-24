package www.mrray.cn.module.forgetpassword

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_forget_password_two.*
import www.mrray.cn.base.BaseViewModelFragment
import www.mrray.cn.repository.login.LoginRepository
import www.mrray.cn.viewmodel.login.LoginViewModel
import www.mrray.cn.R
/**
 * 修改密码第三部
 */
class ForgetCodeThreeFragment:BaseViewModelFragment<LoginViewModel,LoginRepository>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_forget_password_three
    }

    override fun init(view: View, savedInstanceState: Bundle?) {
        super.init(view, savedInstanceState)
        title_bar.setLeftImgListener { it->
            Navigation.findNavController(it).popBackStack()
        }
    }

}