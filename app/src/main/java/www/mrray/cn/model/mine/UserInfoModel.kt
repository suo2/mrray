package www.mrray.cn.model.mine

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserInfoModel(val level: String, val name: String, val accountName: String?, val phoneNumber: String?,val IMName:String?) : Parcelable