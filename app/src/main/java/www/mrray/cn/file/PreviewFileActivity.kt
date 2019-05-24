package www.mrray.cn.file

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_file_preview.*
import www.mrray.cn.R
import www.mrray.cn.base.BaseRepository
import www.mrray.cn.base.BaseViewModel
import www.mrray.cn.base.BaseViewModelActivity
import www.mrray.cn.repository.login.LoginRepository
import www.mrray.cn.viewmodel.login.LoginViewModel

/**
 * 文件预览和下载页面
 */
@SuppressLint("Registered")
class PreviewFileActivity : BaseViewModelActivity<LoginViewModel, LoginRepository>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_file_preview
    }


    override fun init(savedInstanceState: Bundle?) {
        super.init(savedInstanceState)
        preview_webview.settings.javaScriptEnabled = true
        preview_webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        val realUrl = "https://d11.baidupcs.com/file/3ddd3f100d5869651f7bda70b2f69df8?bkt=p3-14003ddd3f100d5869651f7bda70b2f69df853624d2a000000054c00&xcode=c5593e262915bd4c7c6351566b661f5ed373002ce1b7265f87e785fa75afaaac686ca5725030d0975fdf218dc07ace220b2977702d3e6764&fid=53562783-250528-902794144949997&time=1539847818&sign=FDTAXGERLQBHSK-DCb740ccc5511e5e8fedcff06b081203-UQfGpmhBrsKdWqQ5dtRSRH9du%2Bw%3D&to=d11&size=347136&sta_dx=347136&sta_cs=902&sta_ft=doc&sta_ct=7&sta_mt=6&fm2=MH%2CYangquan%2CAnywhere%2C%2Csichuan%2Ccmnet&resv0=cdnback&resv1=0&vuk=53562783&iv=0&htype=&newver=1&newfm=1&secfm=1&flow_ver=3&pkey=14003ddd3f100d5869651f7bda70b2f69df853624d2a000000054c00&sl=76480590&expires=8h&rt=pr&r=613313533&mlogid=6740902394481370499&vbdid=724298808&fin=java%E5%9F%BA%E7%A1%80%E6%80%BB%E7%BB%93.doc&fn=java%E5%9F%BA%E7%A1%80%E6%80%BB%E7%BB%93.doc&rtype=1&dp-logid=6740902394481370499&dp-callid=0.1.1&hps=1&tsl=80&csl=80&csign=p%2FOzjA%2BjsaMZzpTIdeuT6DF10zU%3D&so=0&ut=6&uter=4&serv=0&uc=876084948&ti=b2375bf93e10fe6924a1c9a15ea3429cbc14971310910388&by=themis"
        val finalUrl = "http://view.officeapps.live.com/op/view.aspx?src=$realUrl"
        preview_webview.loadUrl(finalUrl)
    }
}