package www.mrray.cn.module.gp

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * @author suo
 * @date 2018/10/23
 * @desc: 间距
 */
internal class SpaceItemDecoration(private val space: Int = 20) : RecyclerView.ItemDecoration() {

    var total : Int = 0

    constructor(  space: Int ,  total : Int) : this(space){
        this.total = total
    }

    override fun getItemOffsets(outRect: Rect, view: View,
                                parent: RecyclerView, state: RecyclerView.State) {
//        if (parent.getChildAdapterPosition(view) == 0)
//            outRect.top = space
        if (parent.getChildAdapterPosition(view) == total - 1){
            return
        }
        outRect.bottom = space
    }
}
