package www.mrray.cn.base

import android.arch.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import www.mrray.cn.customexception.HttpException
import www.mrray.cn.http.RequestApi
import www.mrray.cn.http.RequestService
import www.mrray.cn.model.http.BaseHttpModel

/**
 * 数据操作基础类
 */
open class BaseRepository {
    open var mRequestService: RequestService = RequestApi.instance.getService()

    private lateinit var mErrorModel: MutableLiveData<HttpException>

    /**
     * 注册错误返回model
     */
    fun registerErrorModel(errorModel: MutableLiveData<HttpException>) {
        this.mErrorModel = errorModel
    }

    private val mCompositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    fun addSubscribe(disposable: Disposable) {
        mCompositeDisposable.add(disposable)
    }

    fun unSubScribe() {
        mCompositeDisposable.dispose()
    }

    /**
     * 封装下 不暴露实现
     */
    fun <T, V : BaseRepository> getRequestApi(observe: Observable<BaseHttpModel<T>>, observable: BaseObserver<T, V>) =
            observe.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observable)

    /**
     * 封装下 不暴露实现
     */
    fun <T> getRequestApi(observe: Observable<BaseHttpModel<T>>): Observable<BaseHttpModel<T>>? =
            observe.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())


    /**
     * 封装下 不暴露实现
     */
    fun <T, V : BaseRepository> getRequestApi(observe: Observable<BaseHttpModel<T>>, success: (T?) -> Unit, error: (HttpException) -> Unit) {
        observe.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : BaseObserver<T, V>(this as V, mErrorModel) {
                    override fun onNewError(e: HttpException) {
                        super.onNewError(e)
                        error(e)
                    }

                    override fun onNewNext(t: T?) {
                        super.onNewNext(t)
                        success(t)
                    }
                })
    }

    /**
     * 封装下 不暴露实现
     */
    fun <T, V : BaseRepository> getRequestApi(observe: Observable<BaseHttpModel<T>>, success: (T?) -> Unit) {
        observe.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : BaseObserver<T, V>(this as V, mErrorModel) {
                    override fun onNewNext(t: T?) {
                        super.onNewNext(t)
                        success(t)
                    }
                })
    }
}