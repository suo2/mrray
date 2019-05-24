package www.mrray.cn.module.mine

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_user_info_face.*
import kotlinx.android.synthetic.main.layout_face_auth.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import www.mrray.cn.R
import www.mrray.cn.base.BaseViewModelActivity
import www.mrray.cn.base.BaseViewModelFragment
import www.mrray.cn.base.MainApplication
import www.mrray.cn.http.RequestApi
import www.mrray.cn.model.login.LoginModel
import www.mrray.cn.module.login.FaceRegisterActivity
import www.mrray.cn.module.login.RegisterTakePhotoActivity
import www.mrray.cn.repository.login.LoginRepository
import www.mrray.cn.repository.mine.MineRepository
import www.mrray.cn.utils.DataCatchInfoUtils
import www.mrray.cn.viewmodel.mine.MineViewModel

/**
 * 人脸识别登录
 */
class UserInfoAuthFaceActivity : BaseViewModelActivity<MineViewModel, MineRepository>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_user_info_face
    }

    override fun init(savedInstanceState: Bundle?) {
        super.init(savedInstanceState)
        mine_face_switch.isChecked = DataCatchInfoUtils(this.applicationContext).getOpenFaceAUth()
        mine_face_switch.setOnCheckedChangeListener { _, isChecked ->
            mDataCatch.setOpenFaceAuth(isChecked)
        }
        mine_face_auth_set_face.setOnClickListener {
            face_auth_password_layout.visibility = View.VISIBLE
        }
        cancel.setOnClickListener {
            face_auth_password_layout.visibility = View.GONE
        }

        cancel_btn.setOnClickListener {
            face_auth_password_layout.visibility = View.GONE
        }

        title_bar.setLeftImgListener { finish() }

        sure_btn.setOnClickListener {
            val password = face_auth_password.text.toString().trim()
            if (password.isEmpty()) {
                toast("请输入密码")
            } else {
                progress.visibility = View.VISIBLE
                RequestApi.instance.login(password, mDataCatch.getUserInfo().accountName ?: "")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            progress.visibility = View.GONE
                            if (it.code == 1) {
                                startActivity<RegisterTakePhotoActivity>(RegisterTakePhotoActivity.EXTE_PHONE to mDataCatch.getUserInfo().phoneNumber,
                                        RegisterTakePhotoActivity.EXTRA_USERNAME to mDataCatch.getUserInfo().accountName)
                                finish()
                            } else {
                                toast("密码输入错误，请重新输入")
                            }
                        }, {
                            progress.visibility = View.GONE
                            toast("网络异常")
                        })
            }
        }

    }


}