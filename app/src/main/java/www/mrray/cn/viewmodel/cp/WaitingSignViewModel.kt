package www.mrray.cn.viewmodel.cp

import www.mrray.cn.base.BaseViewModel
import www.mrray.cn.repository.cp.WaitingSignRepository

class WaitingSignViewModel : BaseViewModel<WaitingSignRepository>() {
    override fun getRepository(): WaitingSignRepository = WaitingSignRepository()
}