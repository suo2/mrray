package www.mrray.cn.http

import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*
import www.mrray.cn.model.PageBaseModel
import www.mrray.cn.model.cp.BusinessSummaryModel
import www.mrray.cn.model.cp.ProjectManageInfoModel
import www.mrray.cn.model.file.FileModel
import www.mrray.cn.model.gp.*
import www.mrray.cn.model.http.BaseHttpModel
import www.mrray.cn.model.login.LoginModel
import www.mrray.cn.model.lp.*
import www.mrray.cn.model.mine.UserInfoModel


interface RequestService {

    /**
     * 获取图片验证码
     */
    @GET("/account/getVerificationCode")
    fun getVerificationCode(): Observable<BaseHttpModel<String>>


    @POST("/account/login")
    fun login(@Body body: RequestBody)
            : Observable<BaseHttpModel<ArrayList<LoginModel>>>

    /**
     * 注册用户发送手机验证码
     */
    @GET("/account/registerPhoneCode")
    fun getPhoneCode(@Query("cellphoneNumber") cellphoneNumber: String): Observable<BaseHttpModel<String>>

    /**
     * 验证手机验证码
     */
    @GET("/account/verificationCodeApp")
    fun verificationPhoneCode(@Query("code") code: String, @Query("token") token: String): Observable<BaseHttpModel<String>>

    //GP
    /**
     * 首页获取头信息
     */
    @GET("/common/head")
    fun getHead(): Observable<BaseHttpModel<HomeHeadModel>>

//    /**
//     * 首页优质项目
//     */
//    @POST("/common/projectPageQuery")
//    fun getProjectPageQuery(@Body body: RequestBody) : Observable<BaseHttpModel<PageBaseModel<HighProjectModel>>>

    /**
     * 首页优质项目
     */
    @GET("/common/projectPageQuery")
    fun getProjectPageQuery(@QueryMap map: Map<String, String>): Observable<BaseHttpModel<PageBaseModel<HighProjectModel>>>

    /**
     * 首页优质客户
     */
    @GET("/common/investorPageQuery")
    fun getInvestorPageQuery(@QueryMap map: Map<String, String>): Observable<BaseHttpModel<PageBaseModel<HighCustomerModel>>>

    /**
     * 根据角色ID获取基金列表
     */
    @GET("/gpManageLp/getFundList")
    fun getFundList(@Query("roleId") roleId: Int): Observable<BaseHttpModel<ArrayList<FundListModel>>>

    /**
     * 基金管理列表
     */
    @GET("/gpmtgt/getFundByRoleId")
    fun getFundByRoleId(@QueryMap map: Map<String, String>): Observable<BaseHttpModel<PageBaseModel<FundsManageModel>>>

    /**
     * 根据基金获取投资人分页列表 fundId为0查询全部
     */
    @GET("/gpManageLp/getLpListByPage")
    fun getLpListByPage(@QueryMap map: Map<String, String>): Observable<BaseHttpModel<PageBaseModel<LpManageModel>>>

    /**
     * 精选基金
     */
    @GET("/common/fundPageQuery")
    fun getFundPageQuery(@QueryMap map: Map<String, String>): Observable<BaseHttpModel<PageBaseModel<FineSelectFundModel>>>

    /**
     * 获取个人信息
     */
    @POST("/account/getSFbyRole")
    fun getUserInfo(@Body body: RequestBody): Observable<BaseHttpModel<UserInfoModel>>

    /**
     * 人脸识别登录
     */
    @POST("/account/faceLogin")
    fun faceLogin(@Body body: RequestBody): Observable<BaseHttpModel<ArrayList<LoginModel>>>

    /**
     * 人脸识别注册
     */
    @POST("/account/uploadHeadPortrait")
    fun faceRegister(@Body body: RequestBody): Observable<BaseHttpModel<String>>

    @GET("/account/getHeadPortrait")
    fun getHeadPortrait(@Query("accountName") accountName: String): Observable<BaseHttpModel<String>>

    /**
     * 获取基金文件列表
     */
    @GET("/fundProcess/getFundProjectProgressFileList")
    fun getFundProjectFileList(@Query("fundId") fundId: Int, @Query("projectId") projectId: Int, @Query("type") type: Int)
            : Observable<BaseHttpModel<ArrayList<FileModel>>>

    /**
     * 基金详情
     */
    @GET("/gpmtgt/findOneFundForGP")
    fun getFoundDetailGP(@Query("fundId") foundId: Int): Observable<BaseHttpModel<FoundDetailModel>>

    /**
     * 获取LP文件
     */
    @GET("/lpManager/fundFile")
    fun getLpFileListData(@Query("fundId") foundId: Int, @Query("fileType") fileType: Int): Observable<BaseHttpModel<ArrayList<FileModel>>>

    /**
     * 验证手机号
     */
    @POST("/account/getAccountInfoByPhone")
    fun verificationPhoneNum(@Body body: RequestBody): Observable<BaseHttpModel<String>>

    /**
     * 验证用戶名
     */
    @POST("/account/getAccountInfoByUserName")
    fun verificationUserName(@Body body: RequestBody): Observable<BaseHttpModel<String>>

    /**
     * 注冊
     */
    @POST("/account/registeredAccount")
    fun register(@Body body: RequestBody): Observable<BaseHttpModel<Any>>

    /**
     * 获取手机验证码
     */
    @GET("/account/registerPhoneCode")
    fun getRegisterPhoneCode(@Query("cellphoneNumber") phone: String): Observable<BaseHttpModel<String>>

    @GET("/lpManager/fundInvested")
    fun getFundInvested(@QueryMap map: Map<String, String>): Observable<BaseHttpModel<PageBaseModel<InvestedFundModel>>>

    /**
     * 投资中基金管理
     */
    @GET("/lpManager/fundInvesting")
    fun getFundInvesting(@QueryMap map: Map<String, String>): Observable<BaseHttpModel<PageBaseModel<InvestingFundModel>>>

    /**
     * 已投项目管理
     */
    @GET("/lpManager/projectInvested")
    fun getProjectInvested(@QueryMap map: Map<String, String>): Observable<BaseHttpModel<PageBaseModel<InvestedProjectModel>>>

    /**
     * CP管理
     */
    @GET("/project/getProjectManageInfoByRoleId")
    fun getProjectManageInfoByRoleId(@Query("roleId") roleId : Int): Observable<BaseHttpModel<ProjectManageInfoModel>>


    /**
     * 投资中的项目管理
     */
    @GET("/lpManager/projectInvesting")
    fun getProjectInvesting(@QueryMap map: Map<String, String>): Observable<BaseHttpModel<PageBaseModel<InvestingProjectModel>>>

    /**
     * 获取经营总结
     */
    @GET("/gpmtgt/getBusinessSummary")
    fun getBusinessSummary(@Query("projectId") projectId: Int): Observable<BaseHttpModel<ArrayList<BusinessSummaryModel>>>

    /**
     * 获取CP的文件
     */
    @GET("/project/getProjectFileList")
    fun getCPFile(@Query("type") fileType: String, @Query("projectId") projectId: String): Observable<BaseHttpModel<ArrayList<FileModel>>>

    /**
     * 添加经营总结
     */
    @POST("/gpmtgt/addBusinessSummary")
    fun addBusinessSummary(@Body body: RequestBody): Observable<BaseHttpModel<Any>>
}