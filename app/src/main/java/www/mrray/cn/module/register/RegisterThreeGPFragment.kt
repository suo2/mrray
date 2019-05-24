package www.mrray.cn.module.register

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_register_three_gp.*
import www.mrray.cn.R
import www.mrray.cn.base.BaseViewModelFragment
import www.mrray.cn.repository.register.RegisterRepository
import www.mrray.cn.viewmodel.register.RegisterViewModel

class RegisterThreeGPFragment : BaseViewModelFragment<RegisterViewModel, RegisterRepository>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_register_three_gp
    }

    override fun init(view: View, savedInstanceState: Bundle?) {
        super.init(view, savedInstanceState)
        initListener()
    }

    private fun initListener() {
        register_tourist_btn.setOnClickListener {
            setChecked(true, false, false, false)
        }
        register_certification_btn.setOnClickListener {
            setChecked(false, true, false, false)
        }
        register_honor_btn_gp.setOnClickListener {
            setChecked(false, false, true, false)
        }
        register_in_the_tube_gp_btn.setOnClickListener {
            setChecked(false, false, false, true)
        }
    }

    private fun setChecked(tourist: Boolean, certification: Boolean, honor: Boolean, inthetube: Boolean) {
        register_tourist_btn.isChecked = tourist
        register_certification_btn.isChecked = certification
        register_honor_btn_gp.isChecked = honor
        register_in_the_tube_gp_btn.isChecked = inthetube

        register_tourist_gp.visibility = if (tourist) View.VISIBLE else View.GONE
        register_certification_gp.visibility = if (certification) View.VISIBLE else View.GONE
        register_in_the_tube_gp.visibility = if (honor) View.VISIBLE else View.GONE
        register_honor_gp.visibility = if (inthetube) View.VISIBLE else View.GONE
    }
}