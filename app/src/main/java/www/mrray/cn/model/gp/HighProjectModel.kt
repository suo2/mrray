package www.mrray.cn.model.gp

/**
 *@author suo
 *@date 2018/10/15
 *@desc: 优质项目实体
 */
data class HighProjectModel(
        val address: String,
        val amount: String,
        val amountInvested: String,
        val completedAmount: String,
        val fundId: Int,
        val fundLogo: String,
        val fundSize: String,
        val industry: String,
        val investmentTime: String,
        val investmentTrends: String,
        val logoPath: String,
        val managementAgency: String,
        val minInvestmentAmount: String,
        val valuation : String,
        val name: String,
        val percent: String,
        val projectId: Int,
        val projectName: String,
        val rotation: String,
        val startTime: String
)