package www.mrray.cn.model.lp

import java.io.Serializable

/**
 *@author suo
 *@date 2018/10/29
 *@desc: 已投基金实体
 */
data class InvestedFundModel(val id : Int, val name : String , val annualizedIncome : String , val currentAddAssets : String , val currentEnterprise : String , val investmentAmount : String
                             , val returnMultiple : String , val shareholdingRatio : String , val expectedExitTime : String , val investmentTime : String)  : Serializable {
}