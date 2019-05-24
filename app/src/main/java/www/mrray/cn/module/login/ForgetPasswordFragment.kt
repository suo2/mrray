package www.mrray.cn.module.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_forget_password.*
import org.jetbrains.anko.support.v4.toast
import www.mrray.cn.R
import www.mrray.cn.base.BaseViewModelFragment
import www.mrray.cn.repository.login.LoginRepository
import www.mrray.cn.viewmodel.login.LoginViewModel

class ForgetPasswordFragment : BaseViewModelFragment<LoginViewModel, LoginRepository>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_forget_password
    }

    override fun init(view: View, savedInstanceState: Bundle?) {
        super.init(view, savedInstanceState)
        title_bar.setLeftImgListener {
            Navigation.findNavController(it).popBackStack()
        }
        forget_password_code_btn.setOnClickListener { mViewModel.getForgetPasswordMessageCode(forget_password_phone_edit.text.toString().trim()) }
        forget_password_confirm.setOnClickListener {
            mViewModel.forgetPassword(forget_password_phone_edit.text.toString().trim(),
                    forget_password_code_edit.text.toString().trim(),
                    "123",
                    forget_password_password_edit.text.toString().trim(),
                    forget_password_password_sure_edit.toString().trim())
        }
    }

    override fun setUpViewModel() {
        super.setUpViewModel()
        mViewModel.mForgetPasswordModel.observe(this, Observer {
                toast("忘记密码成功！！！~~~")
        })
    }
}