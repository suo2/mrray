package www.mrray.cn.base

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import www.mrray.cn.customexception.HttpException
import www.mrray.cn.utils.CommonParameter
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

@SuppressLint("Registered")
open class BaseViewModelActivity<T : BaseViewModel<V>, V : BaseRepository> : BaseActivity() {
    open lateinit var mViewModel: T

    override fun init(savedInstanceState: Bundle?) {
        mViewModel = ViewModelProviders.of(this).get(getClassType() as Class<T>)
        super.init(savedInstanceState)
        setUpViewModel()
    }

    private fun getClassType(): Type? {
        val type = this::class.java.genericSuperclass as ParameterizedType
        return type.actualTypeArguments[0]
    }

    open fun setUpViewModel() {
        mViewModel.mErrorModel.observe(this, Observer {
            if (it!!.code != CommonParameter.REQUEST_NET_SUCCESS) {
                onNetError(it)
            }
        })
    }

    open fun onNetError(it: HttpException?) {

    }
}