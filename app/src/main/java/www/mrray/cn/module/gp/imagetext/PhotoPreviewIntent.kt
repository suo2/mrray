package www.mrray.cn.module.gp.imagetext.intent

import android.content.Context
import android.content.Intent
import www.mrray.cn.module.gp.imagetext.PhotoPreviewActivity
import java.util.*

/**
 * @author suo
 * @date 2018/10/19
 * @desc: 预览照片
 */
class PhotoPreviewIntent(packageContext: Context) : Intent(packageContext, PhotoPreviewActivity::class.java) {

    /**
     * 照片地址
     * @param paths
     */
    fun setPhotoPaths(paths: ArrayList<String>) {
        this.putStringArrayListExtra(PhotoPreviewActivity.EXTRA_PHOTOS, paths)
    }

    /**
     * 当前照片的下标
     * @param currentItem
     */
    fun setCurrentItem(currentItem: Int) {
        this.putExtra(PhotoPreviewActivity.EXTRA_CURRENT_ITEM, currentItem)
    }
}
