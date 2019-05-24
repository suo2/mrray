package www.mrray.cn.module.mine

import android.arch.lifecycle.Observer
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_vip_home.*
import org.jetbrains.anko.toast
import www.mrray.cn.R
import www.mrray.cn.base.BaseActivity
import www.mrray.cn.base.BaseViewModelActivity
import www.mrray.cn.repository.mine.MineRepository
import www.mrray.cn.utils.DataCatchInfoUtils
import www.mrray.cn.viewmodel.mine.MineViewModel

/**
 * 会员中心
 */
class VipHomeActivity : BaseViewModelActivity<MineViewModel, MineRepository>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_vip_home
    }

    override fun init(savedInstanceState: Bundle?) {
        super.init(savedInstanceState)
        title_bar.setOnClickListener { finish() }
        setType()
        setListener()
    }

    private fun setListener() {
        vip_upgrade_button_txt.setOnClickListener {
            toast("此功能暂未开放")
        }
    }

    private fun setType() {
        user_info_auth_type.text = DataCatchInfoUtils(this.applicationContext).returnUserIdentity()
        vip_identity.text = mDataCatch.getUserInfo().level
    }

    override fun setUpViewModel() {
        super.setUpViewModel()
    }
}