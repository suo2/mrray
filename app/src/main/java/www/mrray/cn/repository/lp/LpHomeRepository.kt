package www.mrray.cn.repository.lp

import www.mrray.cn.base.BaseRepository
import www.mrray.cn.base.MainApplication
import www.mrray.cn.http.RequestApi
import www.mrray.cn.model.PageBaseModel
import www.mrray.cn.model.gp.HighProjectModel
import www.mrray.cn.model.gp.HomeHeadModel
import www.mrray.cn.model.lp.*
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
class LpHomeRepository : BaseRepository() {

    fun getHead(callback : (HomeHeadModel?) -> Unit){
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
    fun getProjectPageQuery(map : HashMap<String,String> ,callback : (PageBaseModel<HighProjectModel>?) -> Unit){
        getRequestApi<PageBaseModel<HighProjectModel>, GpHomeRepository>(
                mRequestService.getProjectPageQuery(map)) {
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
     * 已投基金管理列表
     */
    fun getFundInvested(map : HashMap<String,String>, callback : (PageBaseModel<InvestedFundModel>?) -> Unit){
        getRequestApi<PageBaseModel<InvestedFundModel>, LpHomeRepository>(
                mRequestService.getFundInvested(map)) {
            callback(it)
        }
    }

    /**
     * 投资中基金管理
     */
    fun getFundInvesting(map : HashMap<String,String>, callback : (PageBaseModel<InvestingFundModel>?) -> Unit){
        getRequestApi<PageBaseModel<InvestingFundModel>, LpHomeRepository>(
                mRequestService.getFundInvesting(map)) {
            callback(it)
        }
    }

    /**
     * 已投项目管理
     */
    fun getProjectInvested(map : HashMap<String,String>, callback : (PageBaseModel<InvestedProjectModel>?) -> Unit){
        getRequestApi<PageBaseModel<InvestedProjectModel>, LpHomeRepository>(
                mRequestService.getProjectInvested(map)) {
            callback(it)
        }
    }

    /**
     * 投资中的项目管理
     */
    fun getProjectInvesting(map : HashMap<String,String>, callback : (PageBaseModel<InvestingProjectModel>?) -> Unit){
        getRequestApi<PageBaseModel<InvestingProjectModel>, LpHomeRepository>(
                mRequestService.getProjectInvesting(map)) {
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