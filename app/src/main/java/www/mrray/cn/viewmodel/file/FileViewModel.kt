package www.mrray.cn.viewmodel.file

import android.arch.lifecycle.MutableLiveData
import www.mrray.cn.base.BaseViewModel
import www.mrray.cn.model.file.FileModel
import www.mrray.cn.repository.file.FileRepository

class FileViewModel : BaseViewModel<FileRepository>() {
    override fun getRepository(): FileRepository = FileRepository()

    val mFileListModel: MutableLiveData<ArrayList<FileModel>> = MutableLiveData()

    /**
     * 获取GP的文件列表
     */
    fun getFileListGP(foundId: Int?, productId: Int?, type: Int?) {
        if (foundId == null || productId == null || type == null) {
            toast("参数异常")
        } else {
            mRepository.getFileListGP(foundId, productId, type) {
                if (it == null) {
                    toast("此基金暂无数据")
                } else {
                    mFileListModel.value = it
                }
            }
        }
    }

    /**
     * 获取LP的文件列表
     */
    fun getFileListLp(foundId: Int?, typeId: Int?) {
        if (foundId == null || typeId == null) {
            toast("参数异常")
        } else {
            mRepository.getFileListLp(foundId, typeId) {
                if (it == null) {
                    toast("此基金暂无数据")
                } else {
                    mFileListModel.value = it
                }
            }
        }
    }

    fun getCpFile(fileType: Int?, projectId: Int?) {
        if (fileType == null || projectId == null) {
            toast("参数异常")
        } else {
            mRepository.getCpFile(fileType, projectId) {
                if (it == null) {
                    toast("此基金暂无数据")
                } else {
                    mFileListModel.value = it
                }
            }
        }
    }
}