package www.mrray.cn.repository.todo

import www.mrray.cn.base.BaseRepository

class TodoRepository : BaseRepository() {

    fun getEventInfo(productId: Int, status: Int, callback: (String) -> Unit) {
        callback("aaa")
    }

    fun insertEventInfo(eventContent: String, productId: Int, title: String, function: () -> Unit) {
        function()
    }
}