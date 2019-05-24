package www.mrray.cn.model.cp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BusinessSummaryModel(val businessSummary: String, val createTime: String, val title: String) : Parcelable