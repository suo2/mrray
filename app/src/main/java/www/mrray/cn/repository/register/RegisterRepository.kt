package www.mrray.cn.repository.register

import www.mrray.cn.base.BaseRepository
import www.mrray.cn.http.RequestApi

class RegisterRepository : BaseRepository() {
    private var token: String = ""
    /**
     * 验证手机号
     */
    fun verificationPhoneNum(phone: String, callback: () -> Unit, error: () -> Unit) {
        getRequestApi<String, RegisterRepository>(RequestApi.instance.verificationPhoneNum(phone), {
            callback()
        }) {
            error()
        }
    }

    /**
     * 用户名验重
     */
    fun verificationUserName(phone: String, callback: () -> Unit, error: () -> Unit) {
        getRequestApi<String, RegisterRepository>(RequestApi.instance.verificationUserName(phone), {
            callback()
        }) {
            error()
        }
    }

    /**
     * 手机验证码校验
     */
    fun verficationPhoneCode(code: String, success: () -> Unit) {
        getRequestApi<String, RegisterRepository>(mRequestService.verificationPhoneCode(code, token)) { cc ->
            success()
        }
    }

    /**
     * 注册游客
     */
    fun register(userName: String, password: String, phone: String, code: String, type: Int, function: () -> Unit) {
        getRequestApi<Any, RegisterRepository>(RequestApi.instance.register(password, phone, type, userName)) {
            function()
        }
    }

    /**
     * 获取验证码
     */
    fun getPhoneCode(phone: String, success: () -> Unit) {
        getRequestApi<String, RegisterRepository>(mRequestService.getRegisterPhoneCode(phone)) {
            token = it ?: ""
            success()
        }

    }
}