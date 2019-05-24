package www.mrray.cn.model.gp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class  GpManagerDetailModel(val id:Int,val time:String,val content:String) : Parcelable