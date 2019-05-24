package www.mrray.cn.repository.file

import www.mrray.cn.base.BaseRepository
import www.mrray.cn.model.file.FileModel

class FileRepository : BaseRepository() {

    /**
     * 获取GP的文件
     */
    fun getFileListGP(foundId: Int, productId: Int, type: Int, success: (ArrayList<FileModel>?) -> Unit) {
        getRequestApi<ArrayList<FileModel>, FileRepository>(mRequestService.getFundProjectFileList(foundId, productId, type)) {
            success(it)
        }
    }

    /**
     * 获取LP的文件
     */
    fun getFileListLp(foundId: Int, typeId: Int, success: (ArrayList<FileModel>?) -> Unit) {
        getRequestApi<ArrayList<FileModel>, FileRepository>(mRequestService.getLpFileListData(foundId, fileType = typeId)) {
            success(it)
        }
    }

    /**
     * 获取LP的文件
     */
    fun getCpFile(fileType: Int, projectId: Int, success: (ArrayList<FileModel>?) -> Unit) {
        getRequestApi<ArrayList<FileModel>, FileRepository>(mRequestService.getCPFile(fileType.toString(), projectId.toString())) {
            success(it)
        }
    }


}