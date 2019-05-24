package www.mrray.cn.module.login

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.navigation.Navigation
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.layout_login_phone.*
import kotlinx.android.synthetic.main.layout_login_username.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import www.mrray.cn.R
import www.mrray.cn.base.BaseViewModelFragment
import www.mrray.cn.customexception.HttpException
import www.mrray.cn.module.gp.GPProjectDetailHistoryListActivity
import www.mrray.cn.module.register.dialog.RegisterSuccessDialog
import www.mrray.cn.repository.login.LoginRepository
import www.mrray.cn.utils.MobManager
import www.mrray.cn.utils.Utils
import www.mrray.cn.viewmodel.login.LoginViewModel

/**
 * 登录页面
 */
class LoginFragment : BaseViewModelFragment<LoginViewModel, LoginRepository>() {
    private var mCurrentState = 0//0手机验证码登录 1是密码登录
    private var isShow = false
    override fun getLayoutId(): Int {
        return R.layout.fragment_login
    }

    override fun init(view: View, savedInstanceState: Bundle?) {
        super.init(view, savedInstanceState)
        setView()
        initListener()
    }

    private fun setView() {
        login_face.visibility = if (mDataCatch.getOpenFaceAUth()) View.VISIBLE else View.GONE
        if (mCurrentState == 0) {
            setLoginPhoneGroup(true)
            setLoginUserNameGroup(false)
        } else {
            setLoginPhoneGroup(false)
            setLoginUserNameGroup(true)
        }
    }

    private var mPhoneCodeDispose: Disposable? = null

    @SuppressLint("SetTextI18n")
    private fun initListener() {
        login_register_btn.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_login_to_registerFragment)
        }
        login_forget.setOnClickListener {
//            Navigation.findNavController(it).navigate(R.id.action_login_to_forget_password)
        startActivity<GPProjectDetailHistoryListActivity>()
        }
        login_face.setOnClickListener {
            if (mViewModel.setUserData(login_phone_content_text.text.toString().trim(),
                            login_username_content_text.text.toString().trim())){
                Navigation.findNavController(it).navigate(R.id.action_login_to_face)
            }  else {
                toast("请输入手机号或账号")
            }
        }
        login_phone_btn.setOnClickListener {
            setLoginPhoneGroup(true)
            setLoginUserNameGroup(false)
        }
        login_username_btn.setOnClickListener {
            setLoginPhoneGroup(false)
            setLoginUserNameGroup(true)
        }
        login_qq.setOnClickListener { MobManager.getQqPlatformUserInfo() }
        login_wechat.setOnClickListener { MobManager.getWechatPlatformUserInfo() }
        login_weibo.setOnClickListener { MobManager.getWeiboPlatformUserInfo() }
        login_alipay.setOnClickListener { MobManager.getAlipayPlatformUserInfo() }
        login_btn.setOnClickListener {
            showProgress()
            login()
        }
        //手机号登录
        login_phone_clean_btn.setOnClickListener { login_phone_content_text.setText("") }
        login_phone_it_img.setOnClickListener {
            showProgress()
            mViewModel.getPhoneImgCode(login_phone_content_text.text.toString().trim())
        }
        login_phone_get_it_code_text.setOnClickListener {
            showProgress()
            mViewModel.getPhoneCode(login_phone_content_text.text.toString().trim())
            if (login_phone_content_text.text.toString().trim().isEmpty()) {
                return@setOnClickListener
            }
            mPhoneCodeDispose = Utils.countDown(60).subscribe {
                login_phone_get_it_code_text.isClickable = false
                login_phone_get_it_code_text.text = "$it 秒"
                if (it == 0) {
                    login_phone_get_it_code_text.text = "获取验证码"
                    login_phone_get_it_code_text.isClickable = true
                }
            }
        }

        //账户名登录
        login_username_clean_btn.setOnClickListener { login_username_content_text.setText("") }
        login_username_it_img.setOnClickListener {
            showProgress()
            mViewModel.getUsernameImgCode(login_username_content_text.text.toString().trim())
        }
        login_username_password_look_img.setOnClickListener {
            if (!isShow) {
                login_username_password_edit.transformationMethod = HideReturnsTransformationMethod.getInstance(); } else {
                login_username_password_edit.transformationMethod = PasswordTransformationMethod.getInstance()
            }
            isShow = !isShow
        }

    }


    /**
     * @param isVisible 是否显示
     */
    private fun setLoginUserNameGroup(isVisible: Boolean) {
        if (isVisible) mCurrentState = 1
        login_phone_group.visibility = if (!isVisible) View.VISIBLE else View.GONE
        login_username_group.visibility = if (!isVisible) View.GONE else View.VISIBLE
        login_left_divider.visibility = if (!isVisible) View.VISIBLE else View.GONE
        login_right_divider.visibility = if (!isVisible) View.GONE else View.VISIBLE
        login_username_btn.isChecked = isVisible
        login_phone_btn.isChecked = !isVisible
    }

    /**
     * @param isVisible 是否显示
     */
    private fun setLoginPhoneGroup(isVisible: Boolean) {
        if (isVisible) mCurrentState = 0
        login_phone_group.visibility = if (isVisible) View.VISIBLE else View.GONE
        login_username_group.visibility = if (isVisible) View.GONE else View.VISIBLE
        login_left_divider.visibility = if (isVisible) View.VISIBLE else View.GONE
        login_right_divider.visibility = if (isVisible) View.GONE else View.VISIBLE
        login_username_btn.isChecked = !isVisible
        login_phone_btn.isChecked = isVisible
    }


    /**
     * 登录
     */
    private fun login() {
        if (mCurrentState == 0) {
            mViewModel.loginPhone(login_phone_content_text.text.toString().trim(), login_phone_img_it_code_text.text.toString().trim(),
                    login_phone_message_it_code_text.text.toString().trim())
        } else {
            mViewModel.loginPassword(login_username_content_text.text.toString().trim(),
                    login_username_img_it_code_edit.text.toString().trim(),
                    login_username_password_edit.text.toString().trim())
        }
    }

    override fun setUpViewModel() {
        super.setUpViewModel()
        mViewModel.mLoginModel.observe(this, Observer {
            dismissProgress()
            if (it == null || it.isEmpty()) {
                toast("登录失败，请联系管理员")
                return@Observer
            }
            startActivity<ChoiceIdentityActivity>(ChoiceIdentityActivity.EXTRA_DATA to it)
            activity!!.finish()
           /* val intent = Intent(this.context, ChoiceIdentityActivity::class.java)
            intent.putParcelableArrayListExtra(ChoiceIdentityActivity.EXTRA_DATA, it)
            startActivity(intent)*/
        })
        mViewModel.mPhoneImgCodeModel.observe(this, Observer {
            dismissProgress()
            login_phone_it_img.text = it ?: "点击获取"
        })
        mViewModel.mUserImgCodeModel.observe(this, Observer {
            dismissProgress()
            login_username_it_img.text = it ?: "点击获取"
        })
    }

    override fun onNetError(it: HttpException?) {
        super.onNetError(it)
        dismissProgress()
    }

    private fun dismissProgress() {
        progress.visibility = View.GONE
    }

    private fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mPhoneCodeDispose != null) {
            mPhoneCodeDispose?.dispose()
        }
    }
}