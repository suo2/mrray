package www.mrray.cn.repository.mine

import android.annotation.SuppressLint
import android.graphics.Bitmap
import www.mrray.cn.base.BaseRepository
import www.mrray.cn.base.MainApplication
import www.mrray.cn.http.RequestApi
import www.mrray.cn.model.mine.UserInfoModel
import www.mrray.cn.utils.DataCatchInfoUtils
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.util.Base64
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MineRepository : BaseRepository() {

    /**
     * 获取我的个人信息
     */
    fun getMineData(callback: (UserInfoModel?) -> Unit) {
        getRequestApi<UserInfoModel, MineRepository>(RequestApi.instance
                .getUserInfo(DataCatchInfoUtils(MainApplication.instance!!.applicationContext)
                        .getLoginModel()?.roelId
                        ?: 0)) {
            if (it != null) {
                DataCatchInfoUtils(MainApplication.instance.applicationContext).setUserInfo(it)
                callback(it)
            }
        }
    }

    @SuppressLint("CheckResult")
            /**
     * 获取头像
     */
    fun getHeadInfo(success: (Bitmap) -> Unit) {
        mRequestService.getHeadPortrait(DataCatchInfoUtils(MainApplication.instance.applicationContext).getUserInfo().accountName
                ?: "").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.code==1){
                        val str = it.data
                        val bytes = Base64.decode(str ?: "", Base64.DEFAULT)
                        var bm = createBitmapFromByteData(bytes)
                        val matrix = Matrix()
                        matrix.preRotate(270F)
                        bm = Bitmap.createBitmap(bm, 0, 0, bm.width, bm.height, matrix, true)
                        success(bm)
                    }
                },{

                })
       /* getRequestApi<String, MineRepository>(
                mRequestService.getHeadPortrait(DataCatchInfoUtils(MainApplication.instance.applicationContext).getUserInfo().accountName
                        ?: "")) {
            val bytes = Base64.decode(it ?: "", Base64.DEFAULT)
            var bm = createBitmapFromByteData(bytes)
            val matrix = Matrix()
            matrix.preRotate(270F)
            bm = Bitmap.createBitmap(bm, 0, 0, bm.width, bm.height, matrix, true)
            success(bm)
        }*/
    }

    private fun createBitmapFromByteData(data: ByteArray): Bitmap {
        val options = BitmapFactory.Options()
        options.inSampleSize = 4
        return BitmapFactory.decodeByteArray(data, 0, data.size, options)
    }
}