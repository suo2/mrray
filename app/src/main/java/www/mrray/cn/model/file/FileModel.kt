package www.mrray.cn.model.file

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FileModel(val filePath: String, val fileName: String, val upTime: Long) : Parcelable