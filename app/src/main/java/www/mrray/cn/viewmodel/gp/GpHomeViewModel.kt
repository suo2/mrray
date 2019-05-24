package www.mrray.cn.viewmodel.gp

import android.arch.lifecycle.MutableLiveData
import www.mrray.cn.base.BaseViewModel
import www.mrray.cn.repository.gp.GpHomeRepository

/**
 * @author suo
 * @date 2018/10/30
 * @desc: GP首页
 */
class GpHomeViewModel : BaseViewModel<GpHomeRepository>() {
    override fun getRepository(): GpHomeRepository = GpHomeRepository()
    val mHomeHeadModel : MutableLiveData<Any> = MutableLiveData()
    val mHighProjectModel : MutableLiveData<Any> = MutableLiveData()
    val mHighProjectHomeModel : MutableLiveData<Any> = MutableLiveData()
    val mHighCustomerModel : MutableLiveData<Any> = MutableLiveData()
    val mHighCustomerHomeModel : MutableLiveData<Any> = MutableLiveData()
    val mFundListModel : MutableLiveData<Any> = MutableLiveData()
    val mFundsManageModel : MutableLiveData<Any> = MutableLiveData()
    val mLpManageModel : MutableLiveData<Any> = MutableLiveData()

    fun getHead(){
        mRepository.getHead {
            mHomeHeadModel.value = it
        }
    }

    /**
     * 优质项目（首页）
     */
    fun getProjectPageQueryHome(map: Map<String, String>){
        mRepository.getProjectPageQuery(map) {
            mHighProjectHomeModel.value = it
        }
    }

    /**
     * 优质客户（首页）
     */
    fun getInvestorPageQueryHome(map: Map<String, String>){
        mRepository.getInvestorPageQuery(map) {
            mHighCustomerHomeModel.value = it
        }
    }

    /**
     * 优质项目
     */
    fun getProjectPageQuery(map: Map<String, String>){
        mRepository.getProjectPageQuery(map) {
            mHighProjectModel.value = it
        }
    }

    /**
     * 优质客户
     */
    fun getInvestorPageQuery(map: Map<String, String>){
        mRepository.getInvestorPageQuery(map) {
            mHighCustomerModel.value = it
        }
    }

    /**
     * 基金列表
     */
    fun getFundList(roleId : Int){
        mRepository.getFundList(roleId){
            mFundListModel.value = it
        }
    }

    /**
     * 基金管理列表
     */
    fun getFundByRoleId(map: Map<String, String>){
        mRepository.getFundByRoleId(map) {
            mFundsManageModel.value = it
        }
    }

    /**
     * 根据基金获取投资人分页列表 fundId为0查询全部
     */
    fun getLpListByPage(map: Map<String, String> ){
        mRepository.getLpListByPage(map) {
            mLpManageModel.value = it
        }
    }

}