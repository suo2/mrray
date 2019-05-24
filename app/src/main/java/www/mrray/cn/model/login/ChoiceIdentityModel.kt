package www.mrray.cn.model.login

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChoiceIdentityModel(val id: Int, var isSelect: Boolean = false, val type: Int, val title: String, val des: String) : Parcelable