package www.mrray.cn.model.gp

import java.io.Serializable

/**
 *@author suo
 *@date 2018/10/15
 *@desc: 优质客户实体
 */
data class HighCustomerModel(
        val amount: String,
        val fundCount: String,
        val image: String,
        val industry: String,
        val name: String,
        val projectCount: String,
        val roleId: Int,
        val imname: String
) : Serializable