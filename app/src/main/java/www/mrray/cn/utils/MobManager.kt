package www.mrray.cn.utils

import cn.sharesdk.alipay.friends.Alipay
import cn.sharesdk.framework.Platform
import cn.sharesdk.framework.PlatformActionListener
import cn.sharesdk.tencent.qq.QQ
import cn.sharesdk.framework.ShareSDK
import cn.sharesdk.sina.weibo.SinaWeibo
import cn.sharesdk.wechat.friends.Wechat
import com.orhanobut.logger.Logger
import java.util.HashMap


/**
 * mob 第三方分享登录管理
 */
object MobManager {

    /**
     * 获取QQ用户信息  第三方登录
     */
    fun getQqPlatformUserInfo() {
        getUserInfo(QQ.NAME) { p2, p0 ->
            //TODO 把数据处理后  将需要的传回去
        }
    }

    /**
     * 获取微博用户信息 第三方登录
     */
    fun getWeiboPlatformUserInfo() {
        getUserInfo(SinaWeibo.NAME) { p2, p0 -> }
    }

    /**
     * 获取微信第三方信息
     */
    fun getWechatPlatformUserInfo() {
        getUserInfo(Wechat.NAME) { p2, p0 -> }
    }

    /**
     * 获取支付宝第三方信息
     */
    fun getAlipayPlatformUserInfo() {
        getUserInfo(Alipay.NAME) { p2, p0 -> }
    }

    private fun getUserInfo(name: String, callback: (p2: HashMap<String, Any>?, p0: Platform?) -> Unit) {
        val plat = ShareSDK.getPlatform(QQ.NAME)
        if (plat.isAuthValid) {
            plat.removeAccount(true)
        }
        plat.platformActionListener = object : PlatformActionListener {
            override fun onComplete(p0: Platform?, p1: Int, p2: HashMap<String, Any>?) {
                callback(p2, p0)
            }

            override fun onCancel(p0: Platform?, p1: Int) {
            }

            override fun onError(p0: Platform?, p1: Int, p2: Throwable?) {
                p2?.printStackTrace()
            }
        }
        plat.authorize()
    }

    /**
     * 分享到微博
     */
    fun shareWeibo() {
        sharePlatform(SinaWeibo.NAME)
    }

    /**
     * 分享到QQ
     */
    fun shareQQ() {
        sharePlatform(QQ.NAME)
    }

    /**
     * 分享到微信
     */
    fun shareWechat() {
        sharePlatform(Wechat.NAME)
    }

    /**
     * 分享到朋友圈
     */
    fun shareWechatFriend() {

    }

    /**
     * 分享到QQ空间
     */
    fun shareQQZone() {}

    /**
     * 分享到指定平台
     */
    private fun sharePlatform(name: String) {
        val sp = Platform.ShareParams(name)
        sp.text = "测试分享文本"
        sp.imageUrl = ""
        val platform = ShareSDK.getPlatform(name)
        platform.platformActionListener = object : PlatformActionListener {
            override fun onComplete(p0: Platform?, p1: Int, p2: HashMap<String, Any>?) {
            }

            override fun onCancel(p0: Platform?, p1: Int) {
            }

            override fun onError(p0: Platform?, p1: Int, p2: Throwable?) {
            }
        }
        platform.share(sp)
    }

}