package www.mrray.cn.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import www.mrray.cn.model.login.LoginModel
import www.mrray.cn.model.mine.UserInfoModel

/**
 * 数据存储工具类
 */
class DataCatchInfoUtils(context: Context) {
    private var mShare: SharedPreferences = context.getSharedPreferences("JIAOZI_DATA", Context.MODE_PRIVATE)

    companion object {
        const val GP = 3
        const val LP = 1
        const val CP = 4
        const val FA = 5
        const val SV = 6 //Supervision监管
        private const val IDENTITY = "Identity"
        private const val LOGINMODEL = "loginmodel"
        private const val USERNAME = "username"
        private const val PHONE = "phone"
        private const val USERINFO_JSON = "userinfo_json"
        private const val IS_OPEN_FACE_AUTH = "open_face_auth"
    }

    fun getUserInfo() = Gson().fromJson<UserInfoModel>(mShare.getString(USERINFO_JSON, ""), UserInfoModel::class.java)

    fun loginOut() {
        mShare.edit().clear().apply()
    }

    /**
     * 用户信息
     */
    fun setUserInfo(userInfo: UserInfoModel) {
        val mEdit = mShare.edit()
        mEdit.putString(USERINFO_JSON, Gson().toJson(userInfo))
        mEdit.apply()
    }

    fun setUserSelecteIdentity() {

    }

    /**
     * 设置是否开启人脸识别
     */
    fun setOpenFaceAuth(isOpen: Boolean) {
        val mEdit = mShare.edit()
        mEdit.putBoolean(IS_OPEN_FACE_AUTH, isOpen)
        mEdit.apply()
    }

    /**
     * 获取是否设置开启人脸识别
     */
    fun getOpenFaceAUth() = mShare.getBoolean(IS_OPEN_FACE_AUTH, false)

    /**
     * 设置用户身份
     * GP 0
     * LP 1
     * CP 2
     * FA 3
     */
    fun setUserIdentity(type: Int) {
        val editor = mShare.edit()
        editor.putInt(IDENTITY, type)
        editor.apply()
    }

    fun setLoginModel(loginModel: LoginModel) {
        val editor = mShare.edit()
        editor.putString(LOGINMODEL, Gson().toJson(loginModel))
        editor.apply()
    }

    /**
     * 登录信息
     */
    fun getLoginModel(): LoginModel? {
        val json = mShare.getString(LOGINMODEL, "")
        return if (json.isEmpty())
            null
        else
            Gson().fromJson(json, LoginModel::class.java)
    }

    /**
     * 用户身份
     */
    fun getUserIdentity(): Int {
        return getLoginModel()?.typeId ?: -1
    }

    /**
     * 设置用户名或者手机号
     */
    fun setUserNameOrPhone(username: String, phone: String) {
        val mEdit = mShare.edit()
        mEdit.putString(USERNAME, username)
        mEdit.putString(PHONE, phone)
        mEdit.apply()
    }

    /**
     * 帮助切换各种身份
     */
    fun switchUserIdentity(GpCallBack: () -> Unit, LpCallBack: () -> Unit, CpCallBack: () -> Unit, FaCallBack: () -> Unit, SvCallBack: () -> Unit) {
        when (getUserIdentity()) {
            GP -> {
                GpCallBack()
            }
            LP -> {
                LpCallBack()
            }
            CP -> {
                CpCallBack()
            }
            FA -> {
                FaCallBack()
            }
            SV -> {
                SvCallBack()
            }
        }
    }

    /**
     * 返回身份
     */
    fun returnUserIdentity(): String {
        var type = "GP"
        when (getUserIdentity()) {
            GP -> {
                type = "GP"
            }
            LP -> {
                type = "LP"
            }
            CP -> {
                type = "CP"
            }
            FA -> {
                type = "FA"
            }
            SV -> {
                type = "监"
            }
        }
        return type
    }


}