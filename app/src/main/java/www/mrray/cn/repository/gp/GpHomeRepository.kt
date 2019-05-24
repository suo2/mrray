package www.mrray.cn.repository.gp

import www.mrray.cn.base.BaseRepository
import www.mrray.cn.base.MainApplication
import www.mrray.cn.http.RequestApi
import www.mrray.cn.model.PageBaseModel
import www.mrray.cn.model.gp.*
import www.mrray.cn.model.mine.UserInfoModel
import www.mrray.cn.repository.mine.MineRepository
import www.mrray.cn.utils.DataCatchInfoUtils
import www.mrray.cn.utils.ImManager
import www.mrray.cn.utils.LogUtil

/**
 * @author suo
 * @date 2018/10/30
 * @desc: GP首页
 */
class GpHomeRepository : BaseRepository() {


    fun getHead(callback: (HomeHeadModel?) -> Unit) {
        getRequestApi<HomeHeadModel, GpHomeRepository>(mRequestService.getHead()) {
            callback(it)
        }
        getMineData {
            if (it?.IMName != null)
                ImManager.login(it.IMName, "111") { boolean->
                    if (boolean)
                        LogUtil.d("IM 登录成功"+it.IMName)
                }
        }
    }

    /**
     * 优质项目
     */
    fun getProjectPageQuery(map: Map<String, String>, callback: (PageBaseModel<HighProjectModel>?) -> Unit) {
        getRequestApi<PageBaseModel<HighProjectModel>, GpHomeRepository>(
                mRequestService.getProjectPageQuery(map)) {
            callback(it)
        }
    }

    /**
     * 优质客户
     */
    fun getInvestorPageQuery(map: Map<String, String>, callback: (PageBaseModel<HighCustomerModel>?) -> Unit) {
        getRequestApi<PageBaseModel<HighCustomerModel>, GpHomeRepository>(
                mRequestService.getInvestorPageQuery(map)) {
            callback(it)
        }
    }

    /**
     * 基金列表
     */
    fun getFundList(roleId: Int, callback: (ArrayList<FundListModel>?) -> Unit) {
        getRequestApi<ArrayList<FundListModel>, GpHomeRepository>(
                mRequestService.getFundList(roleId)) {
            callback(it)
        }
    }

    /**
     * 基金管理列表
     */
    fun getFundByRoleId(map: Map<String, String>, callback: (PageBaseModel<FundsManageModel>?) -> Unit) {
        getRequestApi<PageBaseModel<FundsManageModel>, GpHomeRepository>(
                mRequestService.getFundByRoleId(map)) {
            callback(it)
        }
    }

    /**
     * 根据基金获取投资人分页列表 fundId为0查询全部
     */
    fun getLpListByPage(map: Map<String, String>, callback: (PageBaseModel<LpManageModel>?) -> Unit) {
        getRequestApi<PageBaseModel<LpManageModel>, GpHomeRepository>(
                mRequestService.getLpListByPage(map)) {
            callback(it)
        }
    }

    /**
     * 获取我的个人信息
     */
    fun getMineData(callback: (UserInfoModel?) -> Unit) {
        getRequestApi<UserInfoModel, MineRepository>(RequestApi.instance
                .getUserInfo(DataCatchInfoUtils(MainApplication.instance!!.applicationContext)
                        .getLoginModel()?.roelId
                        ?: 0)) {
            if (it != null) {
                DataCatchInfoUtils(MainApplication.instance.applicationContext).setUserInfo(it)
                callback(it)
            }
        }
    }
}