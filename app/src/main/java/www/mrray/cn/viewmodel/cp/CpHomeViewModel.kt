package www.mrray.cn.viewmodel.cp

import android.arch.lifecycle.MutableLiveData
import www.mrray.cn.base.BaseViewModel
import www.mrray.cn.repository.cp.CpHomeRepository

/**
 * @author suo
 * @date 2018/10/30
 * @desc: GP首页
 */
class CpHomeViewModel : BaseViewModel<CpHomeRepository>() {
    override fun getRepository(): CpHomeRepository = CpHomeRepository()

    val mHomeHeadModel : MutableLiveData<Any> = MutableLiveData()
    val mHighCustomerModel : MutableLiveData<Any> = MutableLiveData()
    val mFineSelectFundModel : MutableLiveData<Any> = MutableLiveData()
    val mFineSelectFundInfoModel : MutableLiveData<Any> = MutableLiveData()
    val mProjectManageInfoModel : MutableLiveData<Any> = MutableLiveData()

    fun getHead(){
        mRepository.getHead {
            mHomeHeadModel.value = it
        }
    }

    /**
     * 优质客户
     */
    fun getCustomerPageQuery(map: HashMap<String, String>){
        mRepository.getCustomerPageQuery(map) {
            mHighCustomerModel.value = it
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
     * 管理:主页:根据roleId查询项目管理信息
     */
    fun getProjectManageInfoByRoleId(roleId : Int){
        mRepository.getProjectManageInfoByRoleId(roleId) {
            mProjectManageInfoModel.value = it
        }
    }
}