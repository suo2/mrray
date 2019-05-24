package www.mrray.cn.model.gp

import java.io.Serializable

/**
 *@author suo
 *@date 2018/10/15
 *@desc: 首页head实体
 */
data class HomeHeadModel(val investorsCount : String, val gpCount : String, val enterpriseCount : String, val investmentAmount : String) :Serializable{

}