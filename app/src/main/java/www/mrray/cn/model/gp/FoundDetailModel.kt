package www.mrray.cn.model.gp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FoundDetailModel(val fundName: String, val amount: String,
                            val ytAmount: String, val ktAmount: String,
                            val cpCount: String, val returnMultiple: String,
                            val fundId: String, val irr: String) : Parcelable