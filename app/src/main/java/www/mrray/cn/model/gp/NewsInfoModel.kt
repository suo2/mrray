package www.mrray.cn.model.gp

import java.io.Serializable

/**
 *@author suo
 *@date 2018/10/15
 *@desc: 首页新闻咨询模块
 */
data class NewsInfoModel(val id : String, val newsTitle : String, val imgUrl :Int , val time : String , val from : String , val readCount : String) : Serializable {
}