package www.mrray.cn.model.lp

import java.io.Serializable

/**
 *@author suo
 *@date 2018/10/29
 *@desc: 投资中项目实体
 */
data class InvestingProjectModel(val id : Int, val name : String, val address : String, val amount : String, val completedAmount : String, val industry : String
                                 , val rotation : String, val percent : String, val startTime : String)  : Serializable {
}