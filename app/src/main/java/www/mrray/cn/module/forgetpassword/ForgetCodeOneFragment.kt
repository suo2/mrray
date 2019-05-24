package www.mrray.cn.module.forgetpassword

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_forget_password_one.*
import kotlinx.android.synthetic.main.fragment_login.*
import www.mrray.cn.base.BaseViewModelFragment
import www.mrray.cn.R
import www.mrray.cn.repository.login.LoginRepository
import www.mrray.cn.viewmodel.login.LoginViewModel

class ForgetCodeOneFragment : BaseViewModelFragment<LoginViewModel,LoginRepository>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_forget_password_one
    }

    override fun init(view: View, savedInstanceState: Bundle?) {
        super.init(view, savedInstanceState)
        send_code.setOnClickListener {
            if (!TextUtils.isEmpty(forget_password_phone.text.toString())){
                mViewModel.getForgetPasswordMessageCode(forget_password_phone.text.toString())
            }
        }
        title_bar.setLeftImgListener {
            Navigation.findNavController(it).popBackStack()
        }

        mViewModel.mForgetCodeMessageModel.observe(this, Observer {
            Navigation.findNavController(send_code).navigate(R.id.action_forget_password_two)
        })
    }

}
