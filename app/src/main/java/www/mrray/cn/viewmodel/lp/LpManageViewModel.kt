package www.mrray.cn.viewmodel.gp

import www.mrray.cn.base.BaseViewModel
import www.mrray.cn.repository.lp.LpManageRepository

/**
 * @author suo
 * @date 2018/10/30
 * @desc: GP管理
 */
class LpManageViewModel : BaseViewModel<LpManageRepository>() {
    override fun getRepository(): LpManageRepository = LpManageRepository()
}