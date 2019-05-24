package www.mrray.cn.viewmodel.login

import android.arch.lifecycle.MutableLiveData
import www.mrray.cn.base.BaseViewModel
import www.mrray.cn.model.login.ChoiceIdentityModel
import www.mrray.cn.model.login.LoginModel
import www.mrray.cn.repository.login.LoginRepository


/**
 *登录
 */
class LoginViewModel : BaseViewModel<LoginRepository>() {
    override fun getRepository(): LoginRepository = LoginRepository()

    val mChoiceCompleteModel: MutableLiveData<String> = MutableLiveData()
    val mLoginModel: MutableLiveData<ArrayList<LoginModel>> = MutableLiveData()
    val mForgetPasswordModel: MutableLiveData<String> = MutableLiveData()
    val mChoiceDataModel: MutableLiveData<Any> = MutableLiveData()
    val mPhoneImgCodeModel: MutableLiveData<String> = MutableLiveData()//手机验证码登录图形验证码
    val mUserImgCodeModel: MutableLiveData<String> = MutableLiveData()//用户密码登录图形验证码
    val mForgetCodeMessageModel:MutableLiveData<String> = MutableLiveData()//忘记密码 验证码获取

    var mUserData: String = ""//当前的账号


    /**
     * 人脸识别设置账号
     */
    fun setUserData(phone: String, username: String): Boolean {
        if (!phone.isEmpty()) {
            mUserData = phone
            return true
        } else if (!username.isEmpty()) {
            mUserData = username
            return true
        }

        return false
    }

    /**
     * 手机验证码
     */
    private var mPhoneCode: String? = ""//短信验证码
    private var mPhoneImgCode: String? = ""//手机验证码图形验证码
    private var mUserPswImgCode: String? = ""//用户密码验证码 图形验证码
    //***************************************登录**************************************************//
    /**
     * 手机验证码登录
     */
    fun loginPhone(phone: String, imgCode: String, phoneCode: String) {
        if (phone.isEmpty()) {
            toast("账号不能为空")
            return
        }
        if (imgCode.isEmpty()) {
            toast("图形验证码不能为空")
            return
        }
        if (imgCode != mPhoneImgCode) {
            toast("图形验证码错误")
            return
        }
        if (phoneCode.isEmpty()) {
            toast("验证码不能为空")
            return
        }
        mRepository.loginPhone(phone, phoneCode) {
            //TODO 登录成功后的逻辑
            mLoginModel.value = it
        }
    }

    /**
     * 密码登录
     */
    fun loginPassword(username: String, imgCode: String, password: String) {
        if (username.isEmpty()) {
            toast("账号不能为空")
            return
        }
        if (imgCode.isEmpty()) {
            toast("图形验证码不能为空")
            return
        }
        if (imgCode != mUserPswImgCode) {
            toast("图形验证码错误")
            return
        }
        if (password.isEmpty()) {
            toast("密码不能为空")
            return
        }
        mRepository.loginPassword(username, password) {
            mLoginModel.value = it
        }
    }

    /**
     * 获取图像验证码 手机登录
     */
    fun getPhoneImgCode(phone: String) {
        if (phone.isEmpty()) {
            toast("请输入手机号")
            return
        }
        mRepository.getImgCode {
            mPhoneImgCode = it
            mPhoneImgCodeModel.value = it
        }
    }

    /**
     * 获取图像验证码 用户名登录
     */
    fun getUsernameImgCode(username: String) {
        if (username.isEmpty()) {
            toast("请输账号")
            return
        }
        mRepository.getImgCode {
            mUserPswImgCode = it
            mUserImgCodeModel.value = it
        }
    }

    /**
     * 获取短信验证码
     */
    fun getPhoneCode(phone: String) {
        if (phone.isEmpty()) {
            toast("请输入手机号")
            return
        }
        mRepository.getPhoneCode(phone) {
            toast("短信发送成功")
        }
    }

    //***************************************登录**************************************************//

    //***************************************忘记密码**************************************************//
    /**
     * 忘记密码 获取验证码
     */
    fun getForgetPasswordMessageCode(phone: String) {
        if (phone.isEmpty()) {
            toast("请输手机号")
            return
        }
        mRepository.getForgetPasswordCode(phone) {
            mForgetCodeMessageModel.value = phone
        }
    }

    /**
     * 忘记密码
     */
    fun forgetPassword(phone: String, code: String, serviceCode: String, password: String, surePassword: String) {
        if (phone.isEmpty()) {
            toast("请输手机号")
            return
        }
        if (serviceCode.isEmpty()) {
            toast("请获取验证码")
            return
        }
        if (code.isEmpty()) {
            toast("请输入验证码")
            return
        }
        if (password.isEmpty()) {
            toast("请输入密码")
            return
        }
        if (surePassword.isEmpty()) {
            toast("请确认密码")
            return
        }
        mRepository.forgetPassword(phone, code, serviceCode, password, surePassword) {
            mForgetPasswordModel.value = ""
        }
    }
    //***************************************忘记密码**************************************************//


    //***************************************选择身份**************************************************//
    fun completeChoiceIdentity(mCurrentBean: ChoiceIdentityModel?) {
        //TODO 需求数据结构确定 再写逻辑
        if (mCurrentBean == null) {
            toast("请选择身份")
            return
        }
        mChoiceCompleteModel.value = ""
    }

    fun getChoiceIdentityData(userId: Int) {
        mRepository.getChoiceData(userId) {
            mChoiceDataModel.value = ""
        }
    }

    //***************************************选择身份**************************************************//
}