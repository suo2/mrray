package www.mrray.cn.utils

import android.annotation.SuppressLint
import android.app.Application
import android.widget.Toast
import com.hyphenate.EMCallBack
import com.hyphenate.EMError
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMOptions
import com.hyphenate.easeui.EaseUI
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import www.mrray.cn.base.MainApplication
import www.mrray.cn.customexception.EaseMobException


object ImManager {
    var isLogin = false
    fun init(applicationContext: Application) {
        val options = EMOptions()
// 默认添加好友时，是不需要验证的，改成需要验证
        options.acceptInvitationAlways = false
        EMClient.getInstance().init(applicationContext, options)
//在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(false)
        EaseUI.getInstance().init(applicationContext, options)
    }

    fun login(username: String, password: String, callback: (Boolean) -> Unit) {
        if (isLogin)
            return
        isLogin = true//防止重复登录
        EMClient.getInstance().login(username, password, object : EMCallBack {
            override fun onSuccess() {
                isLogin = true
                EMClient.getInstance().chatManager().loadAllConversations()
                EMClient.getInstance().groupManager().loadAllGroups()
                callback(true)
            }

            override fun onProgress(progress: Int, status: String?) {
            }

            override fun onError(code: Int, error: String?) {
                isLogin = false
                callback(false)
                LogUtil.e("登录聊天服务器失败!------$error")
            }
        })
    }

    fun loginOut() {
        isLogin = false
        EMClient.getInstance().logout(true, object : EMCallBack {

            override fun onSuccess() {
            }

            override fun onProgress(progress: Int, status: String) {
            }

            override fun onError(code: Int, message: String) {
            }
        })
    }

    @SuppressLint("CheckResult")
    fun register(username: String, pwd: String) {
        Observable.just(1)
                .subscribeOn(Schedulers.io())
                .map {
                    EMClient.getInstance().createAccount(username, pwd);
                }.observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                }, {
                    if (it is EaseMobException) {
                        val errorCode = it.errorCode
                        when (errorCode) {
                            EMError.NETWORK_ERROR -> Toast.makeText(MainApplication.instance.applicationContext, "网络异常，请检查网络！", Toast.LENGTH_SHORT).show()
                            EMError.USER_ALREADY_EXIST -> Toast.makeText(MainApplication.instance.applicationContext, "用户已存在！", Toast.LENGTH_SHORT).show()
                            EMError.USER_REG_FAILED -> Toast.makeText(MainApplication.instance.applicationContext, "注册失败，无权限！", Toast.LENGTH_SHORT).show()
                            else -> Toast.makeText(MainApplication.instance.applicationContext, "注册失败: " + it.message, Toast.LENGTH_SHORT).show()
                        }
                    }

                })
    }
}