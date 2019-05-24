package www.mrray.cn.module.register

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_register_one.*
import org.jetbrains.anko.support.v4.toast
import www.mrray.cn.R
import www.mrray.cn.base.BaseViewModelFragment
import www.mrray.cn.repository.register.RegisterRepository
import www.mrray.cn.viewmodel.register.RegisterViewModel
import io.reactivex.disposables.Disposable
import www.mrray.cn.customexception.HttpException
import www.mrray.cn.utils.Utils


class RegisterOneFragment : BaseViewModelFragment<RegisterViewModel, RegisterRepository>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_register_one
    }

    /**
     * 防止内存泄露
     */
    private var dispose: Disposable? = null

    override fun onDetach() {
        super.onDetach()
        if (dispose != null) {
            dispose?.dispose()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun init(view: View, savedInstanceState: Bundle?) {
        super.init(view, savedInstanceState)
        register_one_get_code_txt.setOnClickListener {
            it.isClickable = false
            mViewModel.getPhoneCode(register_one_phone_edit.text.toString().trim())
        }
    }

    /**
     * 由于所有的数据最后一次提交上去 所以前面只做校验
     */
    fun nextStep(success: () -> Unit) {
//        success()
        mViewModel.registerOne(register_one_username_edit.text.toString().trim(),
                register_one_password_edit.text.toString().trim(),
                register_one_password_sure_edit.text.toString().trim(),
                register_one_phone_edit.text.toString().trim(),
                register_one_code_edit.text.toString().trim()) {
            success()
        }
    }

    override fun setUpViewModel() {
        super.setUpViewModel()
        mViewModel.mRegisterOneModel.observe(this, Observer {
            if (!it?.errorMsg.isNullOrEmpty()) {
                toast(it?.errorMsg ?: "网络异常请检查网络")
            } else {
                toast(it?.success ?: "成功")
            }
        })

        mViewModel.mGetPhoneCode.observe(this, Observer {
            toast("验证码已发送")
            dispose = Utils.countDown(60).subscribe {
                register_one_get_code_txt.isClickable = false
                register_one_get_code_txt.text = "$it 秒"
                if (it == 0) {
                    register_one_get_code_txt.text = "获取验证码"
                    register_one_get_code_txt.isClickable = true
                }
            }
        })
    }

    override fun onNetError(it: HttpException?) {
        super.onNetError(it)
        register_one_get_code_txt.isClickable = true
    }
}