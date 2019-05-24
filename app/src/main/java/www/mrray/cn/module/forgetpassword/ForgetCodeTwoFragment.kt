package www.mrray.cn.module.forgetpassword

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import www.mrray.cn.base.BaseViewModelFragment
import www.mrray.cn.repository.login.LoginRepository
import www.mrray.cn.viewmodel.login.LoginViewModel
import www.mrray.cn.R
import com.hyphenate.easeui.R.id.textView
import com.jyn.vcview.VerificationCodeView
import kotlinx.android.synthetic.main.fragment_forget_password_two.*

/**
 *忘记密码第二步
 */
class ForgetCodeTwoFragment : BaseViewModelFragment<LoginViewModel, LoginRepository>() {
    private var isJump = false
    override fun getLayoutId(): Int {
        return R.layout.fragment_forget_password_two
    }

    override fun init(view: View, savedInstanceState: Bundle?) {
        super.init(view, savedInstanceState)
        verificationcodeview.setOnCodeFinishListener { content ->
            //跳转下个界面
            if (!isJump) {
                Navigation.findNavController(this.view!!).navigate(R.id.fragment_forget_password_three)
                isJump = false
            }
        }
        title_bar.setLeftImgListener {
            Navigation.findNavController(it).popBackStack()
        }
    }
}