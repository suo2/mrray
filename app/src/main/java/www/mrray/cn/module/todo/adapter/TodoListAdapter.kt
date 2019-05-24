package www.mrray.cn.module.todo.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import www.mrray.cn.R
import www.mrray.cn.model.todo.TodoModel
import www.mrray.cn.module.todo.holder.TodoHolder

class TodoListAdapter(private val mList: ArrayList<TodoModel>) : RecyclerView.Adapter<TodoHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): TodoHolder =
            TodoHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_todo, p0, false))

    override fun getItemCount(): Int = mList.size

    override fun onBindViewHolder(p0: TodoHolder, p1: Int) {
        p0.onBind(mList[p1])
    }
}