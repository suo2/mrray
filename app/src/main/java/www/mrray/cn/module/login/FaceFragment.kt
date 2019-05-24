package www.mrray.cn.module.login

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_face_tip.*
import org.jetbrains.anko.support.v4.startActivity
import www.mrray.cn.base.BaseViewModelFragment
import www.mrray.cn.repository.login.LoginRepository
import www.mrray.cn.viewmodel.login.LoginViewModel
import www.mrray.cn.R

class FaceFragment : BaseViewModelFragment<LoginViewModel, LoginRepository>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_face_tip
    }

    override fun init(view: View, savedInstanceState: Bundle?) {
        super.init(view, savedInstanceState)
        start_take_photo.setOnClickListener {
           val phone =  mViewModel.mUserData
            startActivity<LoginTakePhotoActivity>(LoginTakePhotoActivity.EXTE_PHONE to phone)
        }
        title_bar.setOnClickListener { Navigation.findNavController(it).popBackStack() }
    }
}