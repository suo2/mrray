package www.mrray.cn.model.gp

import java.io.Serializable

/**
 *@author suo
 *@date 2018/10/17
 *@desc: LP管理
 */
data class LpManageModel (val addAssets: String,
                          val enterpriseAssets: String,
                          val fundName: String,
                          val headImage: String,
                          val investmentAmount: String,
                          val investmentShare : String,
                          val name: String,
                          val roleId: Int,
                          val imname: String) : Serializable