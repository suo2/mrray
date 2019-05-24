package www.mrray.cn.viewmodel.todo

import android.arch.lifecycle.MutableLiveData
import org.jetbrains.anko.toast
import www.mrray.cn.base.BaseViewModel
import www.mrray.cn.base.MainApplication
import www.mrray.cn.module.register.databean.RegisterOneStatus
import www.mrray.cn.repository.todo.TodoRepository

class TodoViewModel : BaseViewModel<TodoRepository>() {
    override fun getRepository(): TodoRepository = TodoRepository()

    val mDataModel: MutableLiveData<String> = MutableLiveData()
    val mSubmitModel: MutableLiveData<String> = MutableLiveData()

    fun getEventInfo(productId: Int, status: Int) {
        mRepository.getEventInfo(productId, status) {
            mDataModel.value = it
        }
    }

    fun insertEventInfo(eventContent: String, productId: Int, title: String) {
        if (eventContent.isEmpty()) {
            MainApplication.instance?.toast("请输入内容")
            return
        }
        if (title.isEmpty()) {
            MainApplication.instance?.toast("请输入标题")
            return
        }
        mRepository.insertEventInfo(eventContent, productId, title) {
            mSubmitModel.value = ""
        }
    }
}