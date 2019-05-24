package www.mrray.cn.module.todo.holder

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import www.mrray.cn.model.todo.TodoModel
import kotlinx.android.synthetic.main.item_todo.*
import org.jetbrains.anko.find
import www.mrray.cn.R

class TodoDownHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val todoName = itemView.find<TextView>(R.id.todo_down_name)
    private val todoTime = itemView.find<TextView>(R.id.todo_down_time)
    private val todoFrom = itemView.find<TextView>(R.id.todo_down_from)
    private val todoContent = itemView.find<TextView>(R.id.todo_down_content)
    private val todoCompleteTxt = itemView.find<TextView>(R.id.todo_down_complete_time)

    @SuppressLint("SetTextI18n")
    fun onBind(data: TodoModel) {
        todoName.text = data.name
        todoTime.text = data.time
        todoFrom.text = data.from
        todoContent.text = data.content
        todoCompleteTxt.text = data.completeTime
    }
}