package www.mrray.cn.base

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.widget.Toast
import www.mrray.cn.customexception.HttpException

/**
 * viewmodel基础类
 */
abstract class BaseViewModel<T : BaseRepository> : ViewModel() {
    var mRepository: T
    val mErrorModel: MutableLiveData<HttpException> = MutableLiveData()

    init {
        mRepository = this.getRepository()
        mRepository.registerErrorModel(mErrorModel)
    }

    abstract fun getRepository(): T

    override fun onCleared() {
        super.onCleared()
        mRepository.unSubScribe()
    }

    fun toast(message: String) {
        mErrorModel.value = HttpException(-1, message)
        Toast.makeText(MainApplication.instance.applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}