package www.mrray.cn.model.login

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginModel(val levelId: Int, val typeId: Int, val roelId: Int) : Parcelable