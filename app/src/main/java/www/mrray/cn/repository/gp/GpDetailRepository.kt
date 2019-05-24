package www.mrray.cn.repository.gp

import www.mrray.cn.base.BaseRepository
import www.mrray.cn.model.gp.FoundDetailModel

class GpDetailRepository : BaseRepository() {

    /**
     * 获取基金详情页上部信息
     */
    fun getGpDetailInfo(fundId: Int, success: (FoundDetailModel?) -> Unit) {
        getRequestApi<FoundDetailModel, GpDetailRepository>(mRequestService.getFoundDetailGP(fundId)) {
            success(it)
        }
    }
}