package www.mrray.cn.module.login

import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_face_tip.*
import org.jetbrains.anko.startActivity
import www.mrray.cn.base.BaseViewModelFragment
import www.mrray.cn.repository.login.LoginRepository
import www.mrray.cn.viewmodel.login.LoginViewModel
import www.mrray.cn.R
import www.mrray.cn.base.BaseViewModelActivity
import www.mrray.cn.utils.DataCatchInfoUtils

class FaceRegisterActivity : BaseViewModelActivity<LoginViewModel, LoginRepository>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_face_tip
    }

    override fun init(savedInstanceState: Bundle?) {
        super.init(savedInstanceState)
        val phone = DataCatchInfoUtils(this.applicationContext).getUserInfo().phoneNumber ?: ""
        val accountName = DataCatchInfoUtils(this.applicationContext).getUserInfo().accountName
                ?: ""
        DataCatchInfoUtils(this.applicationContext).getUserInfo().phoneNumber
        start_take_photo.setOnClickListener {
            startActivity<RegisterTakePhotoActivity>(RegisterTakePhotoActivity.EXTE_PHONE to
                    phone, RegisterTakePhotoActivity.EXTRA_USERNAME to
                    accountName)
            finish()
        }
        title_bar.setOnClickListener { finish() }
    }
}