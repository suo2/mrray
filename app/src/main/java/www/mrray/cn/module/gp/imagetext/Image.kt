package www.mrray.cn.module.gp.imagetext

/**
 * @author suo
 * @date 2018/10/22
 * @desc: 图片实体
 */
class Image(var path: String, var name: String, var time: Long) {

    override fun equals(o: Any?): Boolean {
        try {
            val other = o as Image?
            return this.path.equals(other!!.path, ignoreCase = true)
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }

        return super.equals(o)
    }
}
