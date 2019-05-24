package www.mrray.cn.viewmodel.gp

import www.mrray.cn.base.BaseViewModel
import www.mrray.cn.repository.lp.LpDiscoverRepository

/**
 * @author suo
 * @date 2018/10/30
 * @desc: GP发现
 */
class LpDiscoverViewModel : BaseViewModel<LpDiscoverRepository>() {
    override fun getRepository(): LpDiscoverRepository = LpDiscoverRepository()
}