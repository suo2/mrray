package www.mrray.cn.repository.cp

import www.mrray.cn.base.BaseRepository
import www.mrray.cn.base.MainApplication
import www.mrray.cn.http.RequestApi
import www.mrray.cn.model.PageBaseModel
import www.mrray.cn.model.cp.ProjectManageInfoModel
import www.mrray.cn.model.gp.HighCustomerModel
import www.mrray.cn.model.gp.HomeHeadModel
import www.mrray.cn.model.lp.FineSelectFundModel
import www.mrray.cn.model.mine.UserInfoModel
import www.mrray.cn.repository.gp.GpHomeRepository
import www.mrray.cn.repository.mine.MineRepository
import www.mrray.cn.utils.DataCatchInfoUtils
import www.mrray.cn.utils.ImManager
import www.mrray.cn.utils.LogUtil

/**
 * @author suo
 * @date 2018/10/30
 * @desc: LP首页
 */
class CpHomeRepository : BaseRepository() {

    fun getHead(callback : (HomeHeadModel?) -> Unit){
        getRequestApi<HomeHeadModel, GpHomeRepository>(mRequestService.getHead()) {
            callback(it)
        }
        getMineData {
            if (it?.IMName != null)
                ImManager.login(it.IMName ?: "", "111") { boolean->
                    if (boolean)
                        LogUtil.d("IM 登录成功"+it.IMName)
                }
        }
    }

    /**
     * 优质客户
     */
    fun getCustomerPageQuery(map : HashMap<String,String> ,callback : (PageBaseModel<HighCustomerModel>?) -> Unit){
        getRequestApi<PageBaseModel<HighCustomerModel>, GpHomeRepository>(
                mRequestService.getInvestorPageQuery(map)) {
            callback(it)
        }
    }

    /**
     * 精选基金
     */
    fun getFundPageQuery(map : HashMap<String,String>, callback : (PageBaseModel<FineSelectFundModel>?) -> Unit){
        getRequestApi<PageBaseModel<FineSelectFundModel>, GpHomeRepository>(
                mRequestService.getFundPageQuery(map)) {
            callback(it)
        }
    }

    /**
     * 管理:主页:根据roleId查询项目管理信息
     */
    fun getProjectManageInfoByRoleId(roleId : Int, callback : (ProjectManageInfoModel?) -> Unit){
        getRequestApi<ProjectManageInfoModel, CpHomeRepository>(
                mRequestService.getProjectManageInfoByRoleId(roleId)) {
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