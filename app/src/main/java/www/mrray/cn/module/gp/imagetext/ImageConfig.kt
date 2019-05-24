package www.mrray.cn.module.gp.imagetext

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable

/**
 * @author suo
 * @date 2018/10/22
 * @desc: 读取手机照片的限制参数
 */
class ImageConfig : Parcelable {

    // 图片最小宽度
    var minWidth: Int = 0
    // 图片最小高度
    var minHeight: Int = 0
    // 图片大小，单位字节
    var minSize: Long = 0
    // 照片类型: 例如 { image/jpeg, image/png, ... }
    var mimeType: Array<String>? = null

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(this.minWidth)
        dest.writeInt(this.minHeight)
        dest.writeLong(this.minSize)
        dest.writeStringArray(this.mimeType)
    }

    constructor() {}

    protected constructor(`in`: Parcel) {
        this.minWidth = `in`.readInt()
        this.minHeight = `in`.readInt()
        this.minSize = `in`.readLong()
        this.mimeType = `in`.createStringArray()
    }

    companion object {

        @SuppressLint("ParcelCreator")
        val CREATOR: Parcelable.Creator<ImageConfig> = object : Parcelable.Creator<ImageConfig> {
            override fun createFromParcel(source: Parcel): ImageConfig {
                return ImageConfig(source)
            }

            override fun newArray(size: Int): Array<ImageConfig?> {
                return arrayOfNulls(size)
            }
        }
    }
}
