package www.mrray.cn.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import www.mrray.cn.R


/**
 * 图片加载管理类
 */
object ImageManager {
    private val mRequestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .skipMemoryCache(false)

    /**
     * 圆形头像
     */
    fun cicleCropImage(context: Context, imageView: ImageView, url: String) {
        Glide.with(context).load(url).apply(mRequestOptions.circleCrop().placeholder(R.mipmap.default_header).error(R.mipmap.default_header)).into(imageView)
    }

    /**
     * 圆形头像
     */
    fun cicleCropImage(context: Context, imageView: ImageView, bitmap: Bitmap) {
        Glide.with(context).load(bitmap).apply(mRequestOptions.circleCrop().placeholder(R.mipmap.default_header).error(R.mipmap.default_header)).into(imageView)
    }

    /**
     * 普通的图片加载
     */
    fun loadImage(context: Context, imageView: ImageView, url: String) {
        Glide.with(context).load(url).apply(mRequestOptions).into(imageView)
    }

    fun stringToBitmap(string: String): Bitmap? {
        if (string.isNullOrEmpty())
            return null
        var bitmap: Bitmap? = null
        try {
            val bitmapArray = Base64.decode(string, Base64.DEFAULT)
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.size)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bitmap
    }
}