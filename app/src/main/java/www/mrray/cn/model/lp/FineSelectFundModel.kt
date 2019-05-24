package www.mrray.cn.model.lp

import java.io.Serializable

/**
 *@author suo
 *@date 2018/10/25
 *@desc: 精选基金实体
 */
data class FineSelectFundModel(
        val amountInvested: String,
        val fundId: Int,
        val fundLogo: String,
        val fundSize: String,
        val investmentTime: String,
        val investmentTrends: String,
        val managementAgency: String,
        val minInvestmentAmount: String,
        val name: String,
        val percent : String,
        val startTime: String) : Serializable{
}