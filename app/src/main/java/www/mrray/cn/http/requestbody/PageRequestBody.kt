package www.mrray.cn.http.requestbody

import java.io.Serializable

/**
 *@author suo
 *@date 2018/11/8
 *@desc: 分页显示的列表请求参数
 */

data class PageRequestBody constructor(var direction: String , var property: String = "0",var page : Int = 1 , var size : Int = 20)  : Serializable {
    private var map = HashMap<String , String>()

    fun addValue(key : String , value : String): HashMap<String, String> {
        getMap()
        map[key] = value
        return map
    }

    fun getMap(): HashMap<String, String>{
        map["direction"] = direction
        map["propertyType"] = property
        map["page"] = page.toString()
        map["size"] = size.toString()
        return map
    }
}