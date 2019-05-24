package www.mrray.cn.module.web

import android.app.ProgressDialog
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.*
import com.github.lzyzsd.jsbridge.BridgeWebViewClient
import kotlinx.android.synthetic.main.activity_jsbridge.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import www.mrray.cn.R
import www.mrray.cn.base.BaseActivity
import www.mrray.cn.file.FileManagerActivity
import www.mrray.cn.module.login.LoginHomeActivity
import www.mrray.cn.module.todo.ToDoActivity
import www.mrray.cn.utils.LogUtil
import com.google.gson.Gson
import www.mrray.cn.file.FileManagerUtils
import zlc.season.rxdownload3.core.Failed
import zlc.season.rxdownload3.core.Normal
import zlc.season.rxdownload3.core.Succeed


/**
 * 和H5交互的webView
 */
class JSBridgeWebViewActivity : BaseActivity() {
    fun loadData() {
        bridge_webview.reload()
    }

    companion object {
        const val EXTRA_URL = "url"
        const val EXTRA_NAME = "name"
    }

    private var isError = false
    private val mProgressBar by lazy { ProgressDialog(this) }
    override fun getLayoutId(): Int {
        return R.layout.activity_jsbridge
    }

    override fun init(savedInstanceState: Bundle?) {
        super.init(savedInstanceState)
        initWebView()
        title_bar.setLeftImgListener { _ ->
            finish()
        }
        title_bar.setTitleContentText(intent.getStringExtra(EXTRA_NAME) ?: "")
        bridge_webview.webChromeClient = object : WebChromeClient() {
            override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
                return super.onJsAlert(view, url, message, result)
            }
        }
        bridge_webview.webViewClient = object : BridgeWebViewClient(bridge_webview) {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                if (isError) {
//                    error_layout.visibility = View.VISIBLE
                } else {
//                    error_layout.visibility = View.GONE
                }
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                return super.shouldOverrideUrlLoading(view, url)
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                isError = true
            }
        }

        /*   error_layout.setOnClickListener {
               bridge_webview.reload()
               progress.visibility = View.VISIBLE
           }*/
        bridge_webview.settings.javaScriptCanOpenWindowsAutomatically = true
        registerBridge()
    }

    private fun initWebView() {
        val url = intent.getStringExtra(EXTRA_URL)
        LogUtil.d("网页地址------$url")
        bridge_webview.loadUrl(url)
    }


    private fun registerBridge() {

        /**
         * 返回
         */
        bridge_webview.registerHandler("back") { _, _ ->
            finish()
        }

        /**
         * 跳转到新的页面
         */
        bridge_webview.registerHandler("jump_webview") { url, _ ->
            val bean = Gson().fromJson<URLBean>(url, URLBean::class.java)
            startActivity(intentFor<JSBridgeWebViewActivity>(EXTRA_URL to bean.url, EXTRA_NAME to bean.title_name))
        }
        /**
         * 跳转到新的页面
         */
        bridge_webview.registerHandler("submitFromWeb") { url, _ ->
            startActivity(intentFor<JSBridgeWebViewActivity>(EXTRA_URL to url))
        }

        /**
         * 网页打开代办事项
         */
        bridge_webview.registerHandler("open_file_todo_list") { _, _ ->
            LogUtil.d("网页端打开代办事项")
            startActivity<ToDoActivity>()
        }

        /**
         * 登录
         */
        bridge_webview.registerHandler("login") { _, _ ->
            LogUtil.d("网页打开登录")
            startActivity<LoginHomeActivity>()
        }

        /**
         * 打开LP的文件管理
         */
        bridge_webview.registerHandler("open_lp_file") { data, funcation ->
            val datas = getData(data)
            startActivity<FileManagerActivity>(FileManagerActivity.FILE_TYPE_EXTRA to FileManagerActivity.FILE_LP,
                    FileManagerActivity.FILE_FOUND_ID to datas.found_id)
        }
        /**
         * 打开GP的文件管理
         */
        bridge_webview.registerHandler("open_gp_file") { data, funcation ->
            val datas = getData(data)
            startActivity<FileManagerActivity>(FileManagerActivity.FILE_TYPE_EXTRA to FileManagerActivity.FILE_GP,
                    FileManagerActivity.FILE_FOUND_ID to datas.found_id, FileManagerActivity.FILE_PROJECT_ID to datas.found_id)

        }

        bridge_webview.registerHandler("download_file_and_open") { data, _funcation ->
            FileManagerUtils.downLoadFile(data, "文件.doc") { progress, currentStatus, fileUrl ->
                mProgressBar.progress = progress.toInt()
                when (currentStatus) {
                    is Normal -> {
                        mProgressBar.setMessage("下载中...")
                        mProgressBar.show()
                    }
                    is Failed -> {
                        mProgressBar.dismiss()
                    }
                    is Succeed -> {
                        //下载成功代开文件
                        mProgressBar.dismiss()
                        FileManagerUtils.openFile(this, fileUrl)
                    }
                }
            }
        }
    }

    private fun getData(data: String?): URLBean {
        if (!data.isNullOrEmpty()) {
            return Gson().fromJson(data, URLBean::class.java)
        }
        return URLBean()
    }

    override fun onBackPressed() {
        if (bridge_webview.canGoBack()) {
            bridge_webview.goBack()
        } else {
            super.onBackPressed()
        }
    }

    data class URLBean(var url: String = "", var title_name: String = "", var found_id: Int = 0, var project_id: Int = 0)
}