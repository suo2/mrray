package www.mrray.cn.viewmodel.lp

import android.arch.lifecycle.MutableLiveData
import www.mrray.cn.base.BaseViewModel
import www.mrray.cn.repository.lp.LpHomeRepository

/**
 * @author suo
 * @date 2018/10/30
 * @desc: GP首页
 */
class LpHomeViewModel : BaseViewModel<LpHomeRepository>() {
    override fun getRepository(): LpHomeRepository = LpHomeRepository()

    val mHomeHeadModel : MutableLiveData<Any> = MutableLiveData()
    val mHighProjectModel : MutableLiveData<Any> = MutableLiveData()
    val mFineSelectFundModel : MutableLiveData<Any> = MutableLiveData()
    val mFineSelectFundInfoModel : MutableLiveData<Any> = MutableLiveData()
    val mInvestedFundModel : MutableLiveData<Any> = MutableLiveData()
    val mInvestingFundModel : MutableLiveData<Any> = MutableLiveData()
    val mInvestedProjectModel : MutableLiveData<Any> = MutableLiveData()
    val mInvestingProjectModel : MutableLiveData<Any> = MutableLiveData()

    fun getHead(){
        mRepository.getHead {
            mHomeHeadModel.value = it
        }
    }

    /**
     * 优质项目
     */
    fun getProjectPageQuery(map: HashMap<String, String>){
        mRepository.getProjectPageQuery(map) {
            mHighProjectModel.value = it
        }
    }

    /**
     * 精选基金
     */
    fun getFundPageQuery(map: HashMap<String, String>){
        mRepository.getFundPageQuery(map) {
            mFineSelectFundModel.value = it
        }
    }
    /**
     * 精选基金（详细）
     */
    fun getFundPageQueryInfo(map: HashMap<String, String>){
        mRepository.getFundPageQuery(map) {
            mFineSelectFundInfoModel.value = it
        }
    }

    /**
     * 已投基金管理列表
     */
    fun getFundInvested(map: HashMap<String, String>){
        mRepository.getFundInvested(map) {
            mInvestedFundModel.value = it
        }
    }

    /**
     * 投资中基金管理
     */
    fun getFundInvesting(map: HashMap<String, String>){
        mRepository.getFundInvesting(map) {
            mInvestingFundModel.value = it
        }
    }

    /**
     * 已投项目管理
     */
    fun getProjectInvested(map: HashMap<String, String>){
        mRepository.getProjectInvested(map) {
            mInvestedProjectModel.value = it
        }
    }
    /**
     * 投资中的项目管理
     */
    fun getProjectInvesting(map: HashMap<String, String>){
        mRepository.getProjectInvesting(map) {
            mInvestingProjectModel.value = it
        }
    }
}