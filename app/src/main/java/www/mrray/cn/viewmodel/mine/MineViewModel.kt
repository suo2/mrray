package www.mrray.cn.viewmodel.mine

import android.arch.lifecycle.MutableLiveData
import android.graphics.Bitmap
import www.mrray.cn.base.BaseViewModel
import www.mrray.cn.model.mine.UserInfoModel
import www.mrray.cn.repository.mine.MineRepository

class MineViewModel : BaseViewModel<MineRepository>() {
    override fun getRepository(): MineRepository = MineRepository()

    val mUserNameModel: MutableLiveData<UserInfoModel> = MutableLiveData()
    val mHeadImageModel: MutableLiveData<Bitmap> = MutableLiveData()//头像

    /**
     * 获取我的个人信息
     */
    fun getUserInfo() {
        mRepository.getMineData {
            mUserNameModel.value = it
        }
    }

    /**
     * 获取头像
     */
    fun getHeadImageInfo() {
        mRepository.getHeadInfo {
            mHeadImageModel.value = it
        }
    }
}