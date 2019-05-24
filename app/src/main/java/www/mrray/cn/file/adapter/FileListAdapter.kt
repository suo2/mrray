package www.mrray.cn.file.adapter

import android.arch.lifecycle.MutableLiveData
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import www.mrray.cn.R
import www.mrray.cn.file.holder.FileListHolder
import www.mrray.cn.model.file.FileListModel
import www.mrray.cn.model.file.FileModel

class FileListAdapter(private val mList: ArrayList<FileModel>, private val mAdapterModel: MutableLiveData<FileModel>) : RecyclerView.Adapter<FileListHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): FileListHolder =
            FileListHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_file_list, p0, false), mAdapterModel)

    override fun getItemCount(): Int = mList.size

    override fun onBindViewHolder(p0: FileListHolder, p1: Int) {
        p0.onBind(mList[p1])
    }
}