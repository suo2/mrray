package www.mrray.cn.utils

import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import www.mrray.cn.BuildConfig
import kotlin.concurrent.thread

/**
 * 日志工具类  方便统一管理
 */
object LogUtil {
    fun init() {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
                .tag("MRRAY_LOG")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build()

        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }

    fun d(message: String) {
        Logger.d(message)
    }

    fun i(message: String) {
        Logger.i(message)
    }

    fun w(message: String, throwable: Throwable) {
        val info = throwable.message ?: "null"
        Logger.w("$message:$info")
    }

    fun e(message: String, throwable: Throwable) {
        Logger.e(throwable, message)
    }


    fun e(message: String) {
        Logger.e(message)
    }

    fun json(json: String) {
        Logger.json(json)
    }
}