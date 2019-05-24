package www.mrray.cn.base

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import www.mrray.cn.customexception.HttpException
import www.mrray.cn.utils.CommonParameter
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

open class BaseViewModelFragment<T : BaseViewModel<V>, V : BaseRepository> : BaseFragment() {
    open lateinit var mViewModel: T

    override fun init(view: View, savedInstanceState: Bundle?) {
        mViewModel = ViewModelProviders.of(this.activity!!).get(getClassType() as Class<T>)
        super.init(view, savedInstanceState)
        setUpViewModel()
    }

    /**
     * 获取泛型的类型信息
     */
    private fun getClassType(): Type? {
        val type = this::class.java.genericSuperclass as ParameterizedType
        return type.actualTypeArguments[0]
    }

    /**
     * 设置viewmodel 监听
     */
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