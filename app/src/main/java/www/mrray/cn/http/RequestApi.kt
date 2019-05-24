package www.mrray.cn.http

import com.google.gson.Gson
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import www.mrray.cn.BuildConfig
import www.mrray.cn.model.http.BaseHttpModel
import www.mrray.cn.model.login.LoginModel
import www.mrray.cn.model.mine.UserInfoModel
import www.mrray.cn.utils.LogUtil
import java.util.concurrent.TimeUnit

/**
 * 网络请求类
 */
class RequestApi private constructor() {

    private var mRequestService: RequestService

    companion object {
        val instance: RequestApi by lazy { RequestApi() }
    }

    /**
     * 生成okHttp
     */
    private fun okHttpClient(): OkHttpClient {
        val logInterceptor = HttpLoggingInterceptor {
            LogUtil.d(it)
        }.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient()
                .newBuilder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(logInterceptor)
                .build()
    }

    init {
        mRequestService = Retrofit.Builder()
                .baseUrl(BuildConfig.API_HOST)
                .client(okHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .build().create(RequestService::class.java)
    }

    fun getService(): RequestService {
        return mRequestService
    }

    private fun getRequestBody(jsonObject: JSONObject) = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString())!!
    fun login(phoneNumber: String): Observable<BaseHttpModel<ArrayList<LoginModel>>> {
        val jsonObject = JSONObject()
        jsonObject.put("phoneNumber", phoneNumber)
        return mRequestService.login(getRequestBody(jsonObject))
    }

    fun login(password: String, username: String): Observable<BaseHttpModel<ArrayList<LoginModel>>> {
        val jsonObject = JSONObject()
        jsonObject.put("password", password)
        jsonObject.put("userName", username)
        return mRequestService.login(getRequestBody(jsonObject))
    }

    /**
     * 查询个人身份
     * @param id  用户身份ID
     */
    fun getUserInfo(id: Int): Observable<BaseHttpModel<UserInfoModel>> {
        val jsonObject = JSONObject()
        jsonObject.put("id", id)
        return mRequestService.getUserInfo(getRequestBody(jsonObject))
    }

    /**
     * 人脸识别登录
     */
    fun faceLogin(username: String, faceImage: String): Observable<BaseHttpModel<ArrayList<LoginModel>>> {
        val jsonObject = JSONObject()
        jsonObject.put("username", username)
        jsonObject.put("faceImage", faceImage)
        return mRequestService.faceLogin(getRequestBody(jsonObject))
    }

    /**
     * 人脸识别登录
     */
    fun faceRegister(phoneNumber: String, accountName: String, headPortraitImage: String): Observable<BaseHttpModel<String>> {
        val jsonObject = JSONObject()
        jsonObject.put("accountName", accountName)
        jsonObject.put("phoneNumber", phoneNumber)
        jsonObject.put("headPortraitImage", headPortraitImage)
        return mRequestService.faceRegister(getRequestBody(jsonObject))
    }

    /**
     * 验证手机号是否重复
     */
    fun verificationPhoneNum(phoneNum: String): Observable<BaseHttpModel<String>> {
        val jsonObject = JSONObject()
        jsonObject.put("phoneNumber", phoneNum)
        return mRequestService.verificationPhoneNum(getRequestBody(jsonObject))
    }


    /**
     * 验证手机号是否重复
     */
    fun verificationUserName(userName: String): Observable<BaseHttpModel<String>> {
        val jsonObject = JSONObject()
        jsonObject.put("userName", userName)
        return mRequestService.verificationUserName(getRequestBody(jsonObject))
    }

    /**
     * 注册
     */
    fun register(password: String, phone: String, roleconfig: Int, username: String): Observable<BaseHttpModel<Any>> {
        val jsonObject = JSONObject()
        jsonObject.put("password", password)
        jsonObject.put("phone", phone.toLong())
        jsonObject.put("roleconfig", roleconfig)
        jsonObject.put("username", username)
        jsonObject.put("level", "6")
        jsonObject.put("name", username)
        return mRequestService.register(getRequestBody(jsonObject))
    }

    /**
     * 添加经营总结
     */
    fun addBusinessSummary(businessSummary: String, projectId: Int, title: String): Observable<BaseHttpModel<Any>> {
        val jsonObject = JSONObject()
        jsonObject.put("businessSummary", businessSummary)
        jsonObject.put("projectId", projectId)
        jsonObject.put("title", title)
        return mRequestService.addBusinessSummary(getRequestBody(jsonObject))
    }
}