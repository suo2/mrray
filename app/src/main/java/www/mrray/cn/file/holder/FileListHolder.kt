package www.mrray.cn.file.holder

import android.arch.lifecycle.MutableLiveData
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.hyphenate.util.Utils
import org.jetbrains.anko.find
import www.mrray.cn.R
import www.mrray.cn.model.file.FileListModel
import www.mrray.cn.model.file.FileModel

class FileListHolder(itemView: View, val mAdapterModel: MutableLiveData<FileModel>) : RecyclerView.ViewHolder(itemView) {

    val mFileNameTxt = itemView.find<TextView>(R.id.file_name)
    val mFileSize = itemView.find<TextView>(R.id.file_size)
    val mFileTime = itemView.find<TextView>(R.id.file_time)

    fun onBind(fileListModel: FileModel) {
        itemView.setOnClickListener {
            mAdapterModel.value = fileListModel
        }
        mFileNameTxt.text = fileListModel.fileName
        mFileTime.text = www.mrray.cn.utils.Utils.getDateYYYYMMDDHHMM(fileListModel.upTime)
    }
}