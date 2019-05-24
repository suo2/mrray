package www.mrray.cn.module.todo

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_todo.*
import kotlinx.android.synthetic.main.dialog_todo_bottom_sheet.view.*
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import www.mrray.cn.R
import www.mrray.cn.base.BaseViewModelActivity
import www.mrray.cn.file.FileUploadDialog
import www.mrray.cn.module.todo.adapter.TodoPagerAdapter
import www.mrray.cn.repository.todo.TodoRepository
import www.mrray.cn.viewmodel.todo.TodoViewModel

/**
 * 代办事件
 */
class ToDoActivity : BaseViewModelActivity<TodoViewModel, TodoRepository>() {

    companion object {
        const val TODO_TYPE = "todo_type"//0:gp待办   1：cp待办
    }

    private val mTitleList: ArrayList<String> = ArrayList()
    private val mFragmentList: ArrayList<Fragment> = ArrayList()
    override fun getLayoutId(): Int {
        return R.layout.activity_todo
    }

    override fun init(savedInstanceState: Bundle?) {
        super.init(savedInstanceState)
        initData()
        initViewPager()
        initTabLayout()
        title_bar.setLeftImgListener { finish() }
        title_bar.setRightImgListener {
            initBottomSheetDialog()
        }
    }


    @SuppressLint("InflateParams")
    private fun getView(): View {
        val bottomSheetView = LayoutInflater.from(this).inflate(R.layout.dialog_todo_bottom_sheet, null, false)
        bottomSheetView.find<ImageView>(R.id.close).setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        val mTitleEdit = bottomSheetView.find<EditText>(R.id.todo_title_edit)
        val mContentEdit = bottomSheetView.find<EditText>(R.id.todo_content_edit)
        bottomSheetView.find<Button>(R.id.todo_submit).setOnClickListener {
            //TODO 代办事项
            mViewModel.insertEventInfo(mContentEdit.text.toString(), 1, mTitleEdit.text.toString())
        }
        return bottomSheetView
    }

    override fun setUpViewModel() {
        mViewModel.mSubmitModel.observe(this, Observer {
            toast("创建成功")
        })
    }

    private lateinit var bottomSheetDialog: BottomSheetDialog

    private fun initBottomSheetDialog() {
        bottomSheetDialog = BottomSheetDialog(this@ToDoActivity)

        bottomSheetDialog.setCancelable(false)
        bottomSheetDialog.setContentView(getView())
        bottomSheetDialog.show()
    }

    private fun initViewPager() {
        viewpager.adapter = TodoPagerAdapter(this, mTitleList, mFragmentList, supportFragmentManager)
    }

    private fun initData() {
        mTitleList.add("未完成（2）")
        mTitleList.add("已完成（4）")
        mFragmentList.add(TodoListFragment())
        mFragmentList.add(TodoDownListFragment())
    }

    private fun initTabLayout() {
        mTitleList.forEach {
            tab_layout.addTab(tab_layout.newTab().setText(it))
        }
        tab_layout.setupWithViewPager(viewpager)
    }
}