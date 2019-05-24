package www.mrray.cn.model.lp

import java.io.Serializable

/**
 *@author suo
 *@date 2018/10/29
 *@desc: 投资中基金实体
 */
data class InvestingFundModel(val id : Int, val name : String, val amountInvested : String, val fundSize : String, val investmentTrends : String, val managementAgency : String
                              , val minInvestmentAmount : String, val percent : String, val startTime : String, val investmentTime : String)  : Serializable {
}