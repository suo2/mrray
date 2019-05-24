package www.mrray.cn.model.file

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FileListModel(val titleContent: String, val size: String, val time: String, val path: String) : Parcelable