package www.mrray.cn.viewmodel.register

import android.arch.lifecycle.MutableLiveData
import www.mrray.cn.base.BaseViewModel
import www.mrray.cn.module.register.databean.RegisterOneStatus
import www.mrray.cn.repository.register.RegisterRepository

class RegisterViewModel : BaseViewModel<RegisterRepository>() {
    override fun getRepository(): RegisterRepository = RegisterRepository()

    /**
     * 注册账号密码
     */
    val mRegisterOneModel: MutableLiveData<RegisterOneStatus> = MutableLiveData()

    val mRegisterModel: MutableLiveData<Any> = MutableLiveData()

    val mGetPhoneCode: MutableLiveData<String> = MutableLiveData()

    /**
     * 用户密码注册
     * @param  userName 用户账号
     * @param  password 用户密码
     * @param passwordSure 用户确认密码
     * @param phone 手机号
     * @param phoneCode 后台给的验证码
     * @param code 验证码
     * */
    fun registerOne(userName: String, password: String, passwordSure: String, phone: String, code: String, callback: () -> Unit) {
        if (userName.isEmpty()) {
            toast("账号输入错误")
            return
        }
        if (password.isEmpty() or passwordSure.isEmpty()) {
            toast("密码不能为空")
            return
        }
        if (password != passwordSure) {
            toast("两次密码不同，请检查")
            return
        }
        if (phone.isEmpty() || phone.length != 11) {
            toast("手机号输入错误")
            return
        }
        if (code.isEmpty()) {
            toast("请输入验证码")
            return
        }
        this.userName = userName
        this.password = password
        this.phone = phone
        this.code = code
        this.mRepository.verificationPhoneNum(phone, {
            verificationUserName(userName, password, passwordSure, phone, code) {
                callback()
            }
        }) {
//            toast("此手机已被注册")
        }

    }

    /**
     * 手机号验证成功 验证用户名
     */
    private fun verificationUserName(userName: String, password: String, passwordSure: String, phone: String, code: String, success: () -> Unit) {
        mRepository.verificationUserName(userName, {
            verificationPhoneSuccess(userName, password, passwordSure, phone, code) {
                success()
            }
        }, {
//            toast("此用户名已被注册")
        })
    }

    /**
     * 验证手机验证码是否正确
     */
    private fun verificationPhoneSuccess(userName: String, password: String, passwordSure: String, phone: String, code: String, function: () -> Unit) {
        mRepository.verficationPhoneCode(code) {
            function()
        }
    }

    private lateinit var userName: String
    private lateinit var password: String
    private lateinit var passwordSure: String
    private lateinit var phone: String
    private var phoneCode: String? = null
    private lateinit var code: String


    /**
     * 注册游客
     */
    fun register(type: Int) {
        mRepository.register(userName, password, phone, code, type) {
            mRegisterModel.value = ""
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
            mGetPhoneCode.value = ""
        }
    }

}