package www.mrray.cn.exception

import android.app.Application
import www.mrray.cn.utils.LogUtil

class CrashHandler(context: Application) : Thread.UncaughtExceptionHandler {
    private var mApplication: Application? = context
    private var mDefaultHandler: Thread.UncaughtExceptionHandler? = null

    companion object {
        private var crashHandler: CrashHandler? = null
        @Synchronized
        fun getInstance(application: Application): CrashHandler? {
            if (crashHandler == null) {
                crashHandler = CrashHandler(application)
            }
            return crashHandler
        }
    }

    init {
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(t: Thread?, e: Throwable?) {
        LogUtil.e("崩溃",e?:Throwable("崩溃信息是空的~~~"))

       /* //可根据情况选择是否干掉当前的进程
        val intent = Intent(mApplication, GPMainActivity::class.java)
        mApplication!!.alert {
            title = "警告~~"
            message = "崩溃了。。。"
            positiveButton("确定") {
                it.dismiss()
                mApplication!!.startActivity(intent)
            }*/
//        }
    }
}