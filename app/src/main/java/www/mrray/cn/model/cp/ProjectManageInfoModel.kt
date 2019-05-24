package www.mrray.cn.model.cp

/**
 *@author suo
 *@date 2018/11/14
 *@desc:
 */
data class ProjectManageInfoModel constructor(
        val projectId: Int,
        val addAssetsList: List<AddAssets>,
        val earningsValuationList: List<EarningsValuation>,
        val projectName: String,
        val logoPath: String,
        val hisFinanceAmount: String,
        val hisFinanceNum: String,
        val hisFund: String,
        val hisLp: String,
        val operatingStatementList: List<OperatingStatement>,
        val projectEventList: List<ProjectEvent>,
        val projectList: List<Project>
) {
    data class OperatingStatement(
            val xtime: String,
            val yvalue1: Float,
            val yvalue2: Float
    )

    data class ProjectEvent(
            val eventName: String,
            val fileName: String,
            val filePath: String,
            val time: String

    )

    data class AddAssets(
            val xtime: String,
            val yvalue: Float
    )

    data class EarningsValuation(
            val xtime: String,
            val yvalue: Float
    )

    data class Project(
            val financeAmount: String,
            val percent: String,
            val releaseRate: String,
            val rotation: String,
            val startTime: String,
            val valuation: String
    )
}