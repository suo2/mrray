package www.mrray.cn.module.todo.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import www.mrray.cn.R
import www.mrray.cn.file.holder.FileListHolder
import www.mrray.cn.model.todo.TodoModel
import www.mrray.cn.module.todo.holder.TodoDownHolder

class TodoDownListAdapter(private val mList: ArrayList<TodoModel>) : RecyclerView.Adapter<TodoDownHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): TodoDownHolder =
            TodoDownHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_todo_down, p0, false))

    override fun getItemCount(): Int = mList.size

    override fun onBindViewHolder(p0: TodoDownHolder, p1: Int) {
        p0.onBind(mList[p1])
    }
}