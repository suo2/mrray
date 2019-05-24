package www.mrray.cn.module.mine

import android.app.Activity.RESULT_OK
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.hyphenate.easeui.ui.EaseConversationListActivity
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import kotlinx.android.synthetic.main.fragment_mine.*
import kotlinx.android.synthetic.main.layout_mine_info_cp.*
import kotlinx.android.synthetic.main.layout_mine_info_gp.*
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.startActivity
import www.mrray.cn.BuildConfig
import www.mrray.cn.R
import www.mrray.cn.base.BaseViewModelFragment
import www.mrray.cn.event.RegisterFaceEvent
import www.mrray.cn.module.login.LoginHomeActivity
import www.mrray.cn.module.web.JSBridgeWebViewActivity
import www.mrray.cn.repository.mine.MineRepository
import www.mrray.cn.utils.DataCatchInfoUtils
import www.mrray.cn.utils.ImageManager
import www.mrray.cn.viewmodel.mine.MineViewModel
import com.hyphenate.EMCallBack
import com.hyphenate.chat.EMClient
import com.luck.picture.lib.config.PictureMimeType
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.toast
import www.mrray.cn.utils.ImManager


/**
 * 我的页面
 * @author yixin
 */
class MineFragment : BaseViewModelFragment<MineViewModel, MineRepository>() {

    companion object {
        fun newInstance() = MineFragment()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun init(view: View, savedInstanceState: Bundle?) {
        super.init(view, savedInstanceState)
        setUserIdentity()
        initListener()
        getData()
        Bus.observe<RegisterFaceEvent>()
                .subscribe {
                    mViewModel.getHeadImageInfo()
                }
                .registerInBus(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }

    /**
     * 获取各种信息
     */
    private fun getData() {
        //获取姓名和等级
        mViewModel.getUserInfo()
    }

    /**
     * 根据用户身份设置不同的样式
     */
    private fun setUserIdentity() {
        DataCatchInfoUtils(this.requireContext()).switchUserIdentity({
            //GP
            mine_info_gp.visibility = View.VISIBLE
            mine_info_cp.visibility = View.GONE
        }, {}, {
            //Cp
            mine_info_cp.visibility = View.VISIBLE
            mine_info_gp.visibility = View.GONE
        }, {}, {})
    }

    private fun initListener() {
        mine_setting.setOnClickListener {
            //            startActivity<LoginHomeActivity>()
        }
        mine_user_auth.setOnClickListener {
            startActivity<UserInfoAuthActivity>()
        }
        mine_vip.setOnClickListener {
            startActivity<VipHomeActivity>()
        }
        mine_user_setting.setOnClickListener {
            startActivity<UserSettingActivity>()
        }
        mine_head_img_gp.setOnClickListener {
            //            startActivity<LoginHomeActivity>()
              PictureSelector.create(this)
                      .openGallery(PictureMimeType.ofImage())
                      .maxSelectNum(1)
                      .imageSpanCount(4)
                      .selectionMode(PictureConfig.SINGLE)
                      .isCamera(true)
                      .imageFormat(PictureMimeType.PNG)
                      .enableCrop(true)
                      .compress(true)
                      .withAspectRatio(1, 1)
                      .circleDimmedLayer(true)
                      .showCropFrame(false)
                      .showCropGrid(false)
                      .rotateEnabled(true)
                      .scaleEnabled(true)
                      .forResult(PictureConfig.CHOOSE_REQUEST)
        }
        mine_message_notify_item.setOnClickListener {

        }
        mine_userinfo.setOnClickListener {
        }

        kefu_center.setOnClickListener {
        }
        mine_my_message.setOnClickListener {
            //            startActivity<BusinessSummaryActivity>()
//            startActivity<GpManagerDetailActivity>(GpManagerDetailActivity.EXTRA_FOUND_ID to 1)
            startActivity<EaseConversationListActivity>()
        }
        mine_asset_manager.setOnClickListener {
            toast("该用户暂无权限进行此操作")
           /* mDataCatch.switchUserIdentity({
                startActivity(intentFor<JSBridgeWebViewActivity>(JSBridgeWebViewActivity.EXTRA_URL to
                        BuildConfig.H5_HOST + "/GPPlatform/home?roleId=" + mDataCatch.getLoginModel()?.roelId,
                        JSBridgeWebViewActivity.EXTRA_NAME to "资产管理"))
            }, {
                startActivity(intentFor<JSBridgeWebViewActivity>(JSBridgeWebViewActivity.EXTRA_URL to
                        BuildConfig.H5_HOST + "/LPHome?roleId=" + mDataCatch.getLoginModel()?.roelId,
                        JSBridgeWebViewActivity.EXTRA_NAME to "资产管理"))
            }, {
                startActivity(intentFor<JSBridgeWebViewActivity>(JSBridgeWebViewActivity.EXTRA_URL to
                        BuildConfig.H5_HOST + "/CPHome?roleId=" + mDataCatch.getLoginModel()?.roelId,
                        JSBridgeWebViewActivity.EXTRA_NAME to "资产管理"))
            }, {}, {})*/
        }

        login_out.setOnClickListener {
            DataCatchInfoUtils(context!!).loginOut()
            requireContext().startActivity<LoginHomeActivity>()
            activity!!.finish()
            ImManager.loginOut()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                PictureConfig.CHOOSE_REQUEST -> {
                    ImageManager.cicleCropImage(this.context!!, mine_head_img_gp, PictureSelector.obtainMultipleResult(data)[0].cutPath)
                }
            }
        }
    }


    override fun setUpViewModel() {
        super.setUpViewModel()
        mViewModel.mUserNameModel.observe(this, Observer {
            mine_username_txt_gp.text = it?.name
            mine_username_txt_cp.text = it?.name
            mine_user_type_gp.text = DataCatchInfoUtils(this.context?.applicationContext!!).returnUserIdentity()
            mine_user_identity.text = it?.level ?: "普通游客"
            mine_user_identity_cp.text = it?.level ?: "普通游客"
            mine_user_phone.text = it?.phoneNumber ?: "188****888"
            //获取头像
            mViewModel.getHeadImageInfo()
        })
        mViewModel.mHeadImageModel.observe(this, Observer {
            ImageManager.cicleCropImage(this.context!!, mine_head_img_gp, it!!)
            ImageManager.cicleCropImage(this.context!!, mine_head_img_cp, it!!)
        })
    }
}
