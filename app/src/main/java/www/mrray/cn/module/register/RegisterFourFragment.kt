package www.mrray.cn.module.register

import www.mrray.cn.R
import www.mrray.cn.base.BaseViewModelFragment
import www.mrray.cn.repository.register.RegisterRepository
import www.mrray.cn.viewmodel.register.RegisterViewModel

class RegisterFourFragment : BaseViewModelFragment<RegisterViewModel, RegisterRepository>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_register_four
    }
}