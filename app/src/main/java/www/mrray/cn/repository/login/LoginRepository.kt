package www.mrray.cn.repository.login

import android.annotation.SuppressLint
import io.reactivex.Observable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import www.mrray.cn.base.*
import www.mrray.cn.customexception.HttpException
import www.mrray.cn.http.RequestApi
import www.mrray.cn.model.http.BaseHttpModel
import www.mrray.cn.model.login.LoginModel
import www.mrray.cn.utils.CommonParameter
import www.mrray.cn.utils.DataCatchInfoUtils
import www.mrray.cn.utils.ImManager
import www.mrray.cn.utils.LogUtil

class LoginRepository : BaseRepository() {


    private var token: String = ""
    /**
     * 验证码登录
     */
    @SuppressLint("CheckResult")
    fun loginPhone(phone: String, phoneCode: String, callback: (ArrayList<LoginModel>?) -> Unit) {
        getRequestApi<ArrayList<LoginModel>, LoginRepository>(mRequestService.verificationPhoneCode(phoneCode, token)
                .subscribeOn(Schedulers.io())
                .flatMap(Function<BaseHttpModel<String>, Observable<BaseHttpModel<ArrayList<LoginModel>>>> {
                    if (it.code == CommonParameter.REQUEST_NET_SUCCESS) {
                        DataCatchInfoUtils(MainApplication.instance.applicationContext).setUserNameOrPhone("", phone)
                        return@Function RequestApi.instance.login(phone)
                    }
                    return@Function Observable.error(HttpException(0, "验证码错误"))
                })
        ) {
            callback(it)
        }
    }

    /**
     * 密码登录
     */
    fun loginPassword(username: String, password: String, success: (ArrayList<LoginModel>?) -> Unit) {
        getRequestApi<ArrayList<LoginModel>, LoginRepository>(RequestApi.instance.login(password, username)) {
            DataCatchInfoUtils(MainApplication.instance.applicationContext).setUserNameOrPhone(username, "")
            success(it)
        }
    }

    /**
     * 获取图形验证码
     */
    fun getImgCode(success: (String?) -> Unit) {
        getRequestApi<String, LoginRepository>(mRequestService.getVerificationCode()) {
            success(it)
        }
    }

    /**
     * 获取手机验证码
     */
    fun getPhoneCode(phone: String, success: (String?) -> Unit) {
        getRequestApi<String, LoginRepository>(mRequestService.getPhoneCode(phone)) {
            token = it ?: ""
            success(it)
        }
    }

    /**
     * 获取忘记密码的短信验证码
     */
    fun getForgetPasswordCode(phone: String, callback: () -> Unit) {
        callback()
    }

    /**
     * 忘记密码
     */
    fun forgetPassword(phone: String, code: String, serviceCode: String, password: String, surePassword: String, callback: () -> Unit) {
        callback()
    }

    /**
     * 获取身份信息
     */
    fun getChoiceData(userId: Int, callback: () -> Unit) {
        callback()
    }
}