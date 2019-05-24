package www.mrray.cn.base

import android.content.Context
import android.os.Build
import android.os.StrictMode
import android.support.annotation.RequiresApi
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import com.mob.MobSDK
import www.mrray.cn.exception.CrashHandler
import www.mrray.cn.file.FileManagerUtils
import www.mrray.cn.utils.ImManager
import www.mrray.cn.utils.LogUtil


class MainApplication : MultiDexApplication() {

    companion object {
        lateinit var instance: MainApplication
    }


    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun onCreate() {
        super.onCreate()
        instance = this
        MobSDK.init(this)
        initLog()
        //注册异常处理
        CrashHandler.getInstance(this)
        FileManagerUtils.init(this)
        initPhotoError()
        ImManager.init(this)
        //init demo helper
//        DemoHelper.getInstance().init(applicationContext)

        /*  // 请确保环信SDK相关方法运行在主进程，子进程不会初始化环信SDK（该逻辑在EaseUI.java中）
        if (EaseUI.getInstance().isMainProcess(this)) {
            // 初始化华为 HMS 推送服务, 需要在SDK初始化后执行
            HMSPushHelper.getInstance().initHMSAgent(instance)
        }*/
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    /**
     * 初始化日志管理系统
     * 使用方式
     * Logger.d("debug");
     * Logger.e("error");
     * Logger.w("warning");
     * Logger.v("verbose");
     * Logger.i("information");
     * Logger.wtf("What a Terrible Failure");
     */
    private fun initLog() {
        LogUtil.init()
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private fun initPhotoError() {
        // android 7.0系统解决拍照的问题
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        builder.detectFileUriExposure()
    }
}