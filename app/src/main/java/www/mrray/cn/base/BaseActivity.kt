package www.mrray.cn.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import www.mrray.cn.utils.DataCatchInfoUtils
import www.mrray.cn.utils.ImManager
import www.mrray.cn.utils.LogUtil

open class BaseActivity : AppCompatActivity() {
    val mDataCatch by lazy {
        DataCatchInfoUtils(this.applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        init(savedInstanceState)
        loginIm()
    }

    private fun loginIm() {
        if (mDataCatch.getUserInfo() != null && mDataCatch.getUserInfo().IMName != null)
            ImManager.login(mDataCatch.getUserInfo().IMName ?: "", "111") {
                if (it)
                    LogUtil.d("IM 登录成功" + mDataCatch.getUserInfo().IMName)
            }
    }

    open fun getLayoutId(): Int {
        return 0
    }

    open fun init(savedInstanceState: Bundle?) {

    }

}