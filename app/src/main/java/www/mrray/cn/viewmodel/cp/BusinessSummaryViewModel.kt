package www.mrray.cn.viewmodel.cp

import android.arch.lifecycle.MutableLiveData
import www.mrray.cn.base.BaseViewModel
import www.mrray.cn.model.cp.BusinessSummaryModel
import www.mrray.cn.repository.cp.BusinessSummaryRepository

class BusinessSummaryViewModel : BaseViewModel<BusinessSummaryRepository>() {
    override fun getRepository(): BusinessSummaryRepository = BusinessSummaryRepository()

    val mBusinessListModel: MutableLiveData<ArrayList<BusinessSummaryModel>> = MutableLiveData()

    val mAddBusinessModel: MutableLiveData<Any> = MutableLiveData()

    /**
     * 获取经营总结列表
     */
    fun getBusinessSummary(projectId: Int) {
        if (projectId == 0) {
            toast("参数错误")
            return
        }
        mRepository.getBusinessSummary(projectId) {
            if (it != null) {
                mBusinessListModel.value = it
            }
        }
    }

    /**
     * 添加经营总结
     */
    fun addBusinessSummary(businessSummary: String, projectId: Int, title: String) {
        if (projectId == 0 || businessSummary.isEmpty() || title.isEmpty()) {
            toast("参数错误")
            return
        }
        mRepository.addBusinessSummary(businessSummary, projectId, title) {
            toast("添加成功")
            mAddBusinessModel.value = ""
        }
    }
}