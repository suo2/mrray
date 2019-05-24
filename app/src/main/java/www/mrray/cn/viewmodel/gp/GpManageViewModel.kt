package www.mrray.cn.viewmodel.gp

import www.mrray.cn.base.BaseViewModel
import www.mrray.cn.repository.gp.GpManageRepository

/**
 * @author suo
 * @date 2018/10/30
 * @desc: GP管理
 */
class GpManageViewModel : BaseViewModel<GpManageRepository>() {
    override fun getRepository(): GpManageRepository = GpManageRepository()
}