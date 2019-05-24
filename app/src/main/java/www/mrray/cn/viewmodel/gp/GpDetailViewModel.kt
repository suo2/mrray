package www.mrray.cn.viewmodel.gp

import android.arch.lifecycle.MutableLiveData
import www.mrray.cn.base.BaseViewModel
import www.mrray.cn.model.gp.FoundDetailModel
import www.mrray.cn.repository.gp.GpDetailRepository

class GpDetailViewModel : BaseViewModel<GpDetailRepository>() {
    override fun getRepository(): GpDetailRepository = GpDetailRepository()
    val mGpDetailHeadInfoModel: MutableLiveData<FoundDetailModel> = MutableLiveData()

    fun getGpDetailData(fundId: Int) {
        mRepository.getGpDetailInfo(fundId) {
            if (it == null) {
                toast("基金异常")
            } else {
                mGpDetailHeadInfoModel.value = it
            }
        }
    }
}