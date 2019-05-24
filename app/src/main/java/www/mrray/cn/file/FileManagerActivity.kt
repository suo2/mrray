package www.mrray.cn.file

import android.os.Bundle
import android.support.v4.app.Fragment
import kotlinx.android.synthetic.main.activity_file.*
import www.mrray.cn.R
import www.mrray.cn.base.BaseActivity
import www.mrray.cn.file.adapter.FilePagerAdapter
import java.io.Serializable

enum class FileType(var value: Int) : Serializable {
    FILE_FINANCING(1),//融资
    FILE_FINANCE(1),//财务
    FILE_IPO(1),//IPO
    FILE_GAMBLING(1),//对赌
    FILE_MATERIAL(2),//尽调材料materials
    FILE_DAILY(4),//尽调日报
    FILE_CONTRACT(1),//LP合同文件 contract
    FILE_LP_GP(2),//LP基金报表
    FILE_LP_OTHER(4),//LP其他文件
}

/**
 * 文件管理
 */
class FileManagerActivity : BaseActivity() {

    companion object {
        const val FILE_TYPE_EXTRA = "file_type"
        const val FILE_FOUND_ID = "found_id"
        const val FILE_PROJECT_ID = "project_id"
        const val FILE_CP = 4
        const val FILE_GP = 3
        const val FILE_LP = 1
        const val FILE_GP_OR_CP_OR_LP = "gp_cp_lp"
    }

    private val mTitleList: ArrayList<String> = ArrayList()
    private val mFragmentList: ArrayList<Fragment> = ArrayList()
    private var mFoundId = 0
    private var mProjectId = 0

    override fun getLayoutId(): Int {
        return R.layout.activity_file
    }

    override fun init(savedInstanceState: Bundle?) {
        super.init(savedInstanceState)
        initData()
        initViewPager()
        initTabLayout()
        title_bar.setLeftImgListener { finish() }
        title_bar.setRightImgListener {
            val dialog = FileUploadDialog()
            dialog.show(supportFragmentManager, "")
        }
    }

    private fun initViewPager() {
        file_view_pager.adapter = FilePagerAdapter(this, mTitleList, mFragmentList, supportFragmentManager)
    }

    private fun initData() {
        val type = intent.getSerializableExtra(FILE_TYPE_EXTRA)
        mFoundId = intent.getIntExtra(FILE_FOUND_ID, 0)
        mProjectId = intent.getIntExtra(FILE_PROJECT_ID, 0)
        when (type) {
            FILE_GP -> {
                addFragment("尽调材料", FileType.FILE_MATERIAL, FILE_GP)
                addFragment("尽调日报", FileType.FILE_DAILY, FILE_GP)
                file_view_pager.offscreenPageLimit = 2
            }
            FILE_LP -> {
                addFragment("合同文件", FileType.FILE_CONTRACT, FILE_LP)
                addFragment("基金报表", FileType.FILE_LP_GP, FILE_LP)
                addFragment("其他文件", FileType.FILE_LP_OTHER, FILE_LP)
                file_view_pager.offscreenPageLimit = 3
            }
            FILE_CP -> {
                addFragment("融资", FileType.FILE_FINANCING, FILE_CP)
                addFragment("财务", FileType.FILE_FINANCE, FILE_CP)
                addFragment("IPO", FileType.FILE_IPO, FILE_CP)
                addFragment("对赌", FileType.FILE_GAMBLING, FILE_CP)
                file_view_pager.offscreenPageLimit = 4
            }
        }
    }

    private fun addFragment(title: String, type: FileType, status: Int) {
        val fragment = FileListFragment()
        val bundle = Bundle()
        bundle.putInt(FILE_PROJECT_ID, mProjectId)
        bundle.putInt(FILE_FOUND_ID, mFoundId)
        bundle.putInt(FILE_TYPE_EXTRA, type.value)
        bundle.putInt(FILE_GP_OR_CP_OR_LP, status)
        fragment.arguments = bundle
        mTitleList.add(title)
        mFragmentList.add(fragment)
    }

    private fun initTabLayout() {
        mTitleList.forEach {
            tab_layout.addTab(tab_layout.newTab().setText(it))
        }
        tab_layout.setupWithViewPager(file_view_pager)
    }
}
