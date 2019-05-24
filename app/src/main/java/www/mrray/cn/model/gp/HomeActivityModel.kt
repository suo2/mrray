package www.mrray.cn.model.gp

import java.io.Serializable

/**
 *@author suo
 *@date 2018/10/15
 *@desc: 首页活动模块
 */
data class HomeActivityModel(val id : String, val title : String, val imgUrl : String, val time : String, val institution : String, val speaker : String) : Serializable {
}