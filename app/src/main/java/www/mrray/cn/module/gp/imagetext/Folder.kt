package www.mrray.cn.module.gp.imagetext

/**
 * @author suo
 * @date 2018/10/22
 * @desc: 文件夹
 */
class Folder {
    var name: String? = null
    var path: String? = null
    var cover: Image? = null
    var images: MutableList<Image>? = null

    override fun equals(o: Any?): Boolean {
        try {
            val other = o as Folder?
            return this.path!!.equals(other!!.path!!, ignoreCase = true)
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }
        return super.equals(o)
    }
}
