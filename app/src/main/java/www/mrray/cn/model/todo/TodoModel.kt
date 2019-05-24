package www.mrray.cn.model.todo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TodoModel(val name: String, val time: String, val from: String, val content: String, val progress: Int, val completeTime: String) : Parcelable