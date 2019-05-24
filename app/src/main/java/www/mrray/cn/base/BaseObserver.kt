package www.mrray.cn.base

import android.arch.lifecycle.MutableLiveData
import android.widget.Toast
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import www.mrray.cn.customexception.HttpException
import www.mrray.cn.model.http.BaseHttpModel
import www.mrray.cn.utils.CommonParameter
import www.mrray.cn.utils.LogUtil

/**
 * 基础网络请求
 */
open class BaseObserver<T, V : BaseRepository> : Observer<BaseHttpModel<T>> {
    private var mRepository: V
    private var mErrorModel: MutableLiveData<HttpException>? = null

    constructor(repository: V) {
        this.mRepository = repository
    }

    constructor(repository: V, errorModel: MutableLiveData<HttpException>) {
        this.mRepository = repository
        this.mErrorModel = errorModel
    }

    override fun onNext(t: BaseHttpModel<T>) {
        if (t.code != CommonParameter.REQUEST_NET_SUCCESS) {
            this.onError(HttpException(t.code, t.message))
        } else {
            mErrorModel?.value = HttpException(CommonParameter.REQUEST_NET_SUCCESS, t.message)//防止toast
            if (t.data == null) {
                onNewNext(null)
            } else {
                onNewNext(t.data)
            }
        }
    }

    open fun onNewNext(t: T?) {

    }

    private lateinit var mDisposable: Disposable
    override fun onComplete() {
        if (!mDisposable.isDisposed) {
            mDisposable.dispose()
        }
    }

    override fun onSubscribe(d: Disposable) {
        mDisposable = d
        mRepository.addSubscribe(d)
    }

    override fun onError(e: Throwable) {
        LogUtil.e(e.message.toString(), e)
        //处理错误信息返回
        when (e) {
            is HttpException -> {
                onNewError(e)
                Toast.makeText(MainApplication.instance, e.httpMessage, Toast.LENGTH_SHORT).show()
                mErrorModel?.value = e
            }
            else -> {
                mErrorModel?.value = HttpException(-1, e.message
                        ?: "网络错误")
                Toast.makeText(MainApplication.instance, e.message, Toast.LENGTH_SHORT).show()
                LogUtil.e(e.message ?: "网络错误", e)
                onNewError(HttpException(-1, e.message ?: "网络错误"))
            }
        }
    }

    open fun onNewError(e: HttpException) {
        /*   Toast.makeText(MainApplication.instance.applicationContext, e.message
                   ?: "网络错误", Toast.LENGTH_SHORT).show()*/
    }
}