package www.mrray.cn.module.gp.imagetext.intent

import android.content.Context
import android.content.Intent
import www.mrray.cn.module.gp.imagetext.ImageConfig
import www.mrray.cn.module.gp.imagetext.PhotoPickerActivity
import www.mrray.cn.module.gp.imagetext.SelectModel
import java.util.*

/**
 * @author suo
 * @date 2018/10/22
 * @desc: 选择照片
 */
class PhotoPickerIntent(packageContext: Context) : Intent(packageContext, PhotoPickerActivity::class.java) {

    fun setShowCarema(bool: Boolean) {
        this.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, bool)
    }

    fun setMaxTotal(total: Int) {
        this.putExtra(PhotoPickerActivity.EXTRA_SELECT_COUNT, total)
    }

    /**
     * 选择
     * @param model
     */
    fun setSelectModel(model: SelectModel) {
        this.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, Integer.parseInt(model.toString()))
    }

    /**
     * 已选择的照片地址
     * @param imagePathis
     */
    fun setSelectedPaths(imagePathis: ArrayList<String>) {
        this.putStringArrayListExtra(PhotoPickerActivity.EXTRA_DEFAULT_SELECTED_LIST, imagePathis)
    }

    /**
     * 显示相册图片的属性
     * @param config
     */
    fun setImageConfig(config: ImageConfig) {
        this.putExtra(PhotoPickerActivity.EXTRA_IMAGE_CONFIG, config)
    }
}
