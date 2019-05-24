package www.mrray.cn.repository.cp

import www.mrray.cn.base.BaseRepository
import www.mrray.cn.http.RequestApi
import www.mrray.cn.model.cp.BusinessSummaryModel

class BusinessSummaryRepository : BaseRepository() {

    /**
     * 获取经营总结列表
     */
    fun getBusinessSummary(projectId: Int, callback: (ArrayList<BusinessSummaryModel>?) -> Unit) {
        getRequestApi<ArrayList<BusinessSummaryModel>, BusinessSummaryRepository>(mRequestService.getBusinessSummary(projectId)) {
            if (it != null)
                callback(it)
        }
    }

    /**
     * 添加经营总结
     */
    fun addBusinessSummary(businessSummary: String, projectId: Int, title: String, success: () -> Unit) {
        getRequestApi<Any, BusinessSummaryRepository>(RequestApi.instance.addBusinessSummary(businessSummary, projectId, title)) {
            success()
        }
    }
}