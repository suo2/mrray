package www.mrray.cn.model.gp

import java.io.Serializable

/**
 *@author suo
 *@date 2018/10/17
 *@desc: 基金管理实体类
 */
data class FundsManageModel(val annualizedIncome: String,
                            val fundId: Int,
                            val fundName: String,
                            val investCompanyCount: String,
                            val lpCount: String) : Serializable
