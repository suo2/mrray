package www.mrray.cn.file

import android.app.ProgressDialog
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.fragment_file_list.*
import www.mrray.cn.R
import www.mrray.cn.base.BaseViewModelFragment
import www.mrray.cn.file.adapter.FileListAdapter
import www.mrray.cn.model.file.FileModel
import www.mrray.cn.repository.file.FileRepository
import www.mrray.cn.viewmodel.file.FileViewModel
import zlc.season.rxdownload3.core.*

/**
 * 文件管理
 */
class FileListFragment : BaseViewModelFragment<FileViewModel, FileRepository>() {

    private val mListData: ArrayList<FileModel> = ArrayList()
    private val mAdapterModel: MutableLiveData<FileModel> = MutableLiveData()
    private val mAdapter = FileListAdapter(mListData, mAdapterModel)
    private lateinit var mProgressBar: ProgressDialog
    private var mType: Int? = 0
    private var mFoundId: Int? = 0
    private var mProductId: Int? = 0
    private var mStatus: Int? = 0

    override fun getLayoutId(): Int {
        return R.layout.fragment_file_list
    }

    override fun init(view: View, savedInstanceState: Bundle?) {
        mViewModel = ViewModelProviders.of(this).get(FileViewModel::class.java)
        setUpViewModel()
        mProgressBar = ProgressDialog(this.context)
        initRecyclerView()
        getArgumentsData()
        getData()
    }

    private fun getArgumentsData() {
        mType = arguments?.getInt(FileManagerActivity.FILE_TYPE_EXTRA)
        mProductId = arguments?.getInt(FileManagerActivity.FILE_PROJECT_ID)
        mFoundId = arguments?.getInt(FileManagerActivity.FILE_FOUND_ID)
        mStatus = arguments?.getInt(FileManagerActivity.FILE_GP_OR_CP_OR_LP)
    }

    private fun getData() {
        when (mStatus) {
            FileManagerActivity.FILE_GP -> {
                mViewModel.getFileListGP(mFoundId, mProductId, mType)
            }
            FileManagerActivity.FILE_LP -> {
                mViewModel.getFileListLp(mFoundId, mType)
            }
            FileManagerActivity.FILE_CP -> {
                mViewModel.getCpFile(mType, mProductId)
            }
        }
    }

    override fun setUpViewModel() {
        super.setUpViewModel()
        mAdapterModel.observe(this, Observer {
            FileManagerUtils.downLoadFile(it!!.filePath, "文件.doc") { progress, currentStatus, fileUrl ->
                mProgressBar.progress = progress.toInt()
                when (currentStatus) {
                    is Normal -> {
                        mProgressBar.setMessage("下载中...")
                        mProgressBar.show()
                    }
                    is Failed -> {
                        mProgressBar.dismiss()
                    }
                    is Succeed -> {
                        //下载成功代开文件
                        mProgressBar.dismiss()
                        FileManagerUtils.openFile(this@FileListFragment.context!!, fileUrl)
                    }
                }
            }
        })

        mViewModel.mFileListModel.observe(this, Observer {
            mListData.addAll(it!!)
            mAdapter.notifyDataSetChanged()
        })
    }

    private fun initRecyclerView() {
        recycler_view.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycler_view.adapter = mAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        FileManagerUtils.dispos()
    }

}