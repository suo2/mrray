package www.mrray.cn.model.web

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * 把数据给JS的bean
 */
@Parcelize
data class WebToJsBean(val roleId: Int, val phone: Int, val userName: String) : Parcelable