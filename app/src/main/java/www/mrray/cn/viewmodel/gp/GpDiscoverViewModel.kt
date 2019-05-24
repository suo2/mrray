package www.mrray.cn.viewmodel.gp

import www.mrray.cn.base.BaseViewModel
import www.mrray.cn.repository.gp.GpDiscoverRepository

/**
 * @author suo
 * @date 2018/10/30
 * @desc: GP发现
 */
class GpDiscoverViewModel : BaseViewModel<GpDiscoverRepository>() {
    override fun getRepository(): GpDiscoverRepository = GpDiscoverRepository()
}