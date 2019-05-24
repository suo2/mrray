package www.mrray.cn.file

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.text.TextUtils
import android.webkit.MimeTypeMap
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import org.jetbrains.anko.toast
import www.mrray.cn.base.MainApplication
import www.mrray.cn.utils.LogUtil
import zlc.season.rxdownload3.RxDownload
import zlc.season.rxdownload3.core.DownloadConfig
import zlc.season.rxdownload3.core.Mission
import zlc.season.rxdownload3.core.Status
import zlc.season.rxdownload3.helper.dispose
import java.io.File
import java.lang.Exception
import java.lang.reflect.Field

/**
 * 文件管理
 */
object FileManagerUtils {
    private val EXTRA_FILE_PATH = getSDPath()

    fun init(application: MainApplication) {
        val builder = DownloadConfig.Builder.create(application)
                .enableAutoStart(true)
                .setDefaultPath(EXTRA_FILE_PATH)
                .useHeadMethod(true)
                .setMaxRange(10)
                .setRangeDownloadSize(4 * 1000 * 1000)
                .setMaxMission(3)
                .enableService(true)
        DownloadConfig.init(builder)
    }

    /**
     * 获取文件存储路径
     */
    private fun getSDPath(): String {
        var sdDir = File("/sdcard/DCIM/MrMayFile/")
        val sdCardExit: Boolean = Environment.getExternalStorageState() == android.os.Environment.MEDIA_MOUNTED
        if (sdCardExit) {
            sdDir = Environment.getExternalStorageDirectory()
        } else {
            if (!sdDir.exists()) {
                sdDir.createNewFile()
            }
            return sdDir.toString()
        }
        return sdDir.toString() + File.separator + "MrMayFile"
    }

    private var disposable: Disposable? = null

    /**
     * 下载文件
     */
    @SuppressLint("CheckResult")
    fun downLoadFile(url: String,fileName:String, callBack: (progress: Double, currentStatus: Status, fileUrl: String) -> Unit) {
        val mission = Mission(url, fileName, getSDPath())
        disposable = RxDownload.create(mission)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ status ->
                    val result = if (status.totalSize == 0L) {
                        0.0
                    } else {
                        status.downloadSize * 1.0 / status.totalSize
                    }
                    callBack(result * 100,
                            status,
                            mission.savePath + File.separator + mission.saveName)
                }, {
                    LogUtil.e("文件下载", it)
                })
    }

    fun starDownloadFile(url: String) {
        RxDownload.start(url).subscribe()
    }

    fun stopDownloadFile(url: String) {
        RxDownload.stop(url).subscribe()
    }

    fun clearDownloadFile(url: String) {
        RxDownload.clear(url).subscribe()
    }

    fun clearDownloadFileAll() {
        RxDownload.clearAll().subscribe()
    }

    /**
     * 生命周期结束调用 防止泄露
     */
    fun dispos() {
        if (disposable != null)
            dispose(disposable)
    }

    /**
     * 根据文件后缀名匹配MIMEType
     * @param file
     * @return
     */
    fun getMIMEType(file: File): String {
        var type = "*/*"
        var name = file.name
        var index = name.lastIndexOf('.')
        if (index < 0) {
            return type
        }

        val end = name.substring(index, name.length).toLowerCase()
        if (TextUtils.isEmpty(end)) return type

        for (i in 0..MIME_MapTable.size + 1) {
            var s = MIME_MapTable[i][0]
            LogUtil.d("$end ......$s")
            if (end == MIME_MapTable[i][0]) {
                type = MIME_MapTable[i][1]
                return type
            }
        }
        return type
    }

    /**
     * 打开文件
     * @param context
     * @param file 文件
     */
    fun openFile(context: Context, url: String) {
        try {
            val file = File(url)
            if (!file.exists()) {
                context.toast("文件不存在，请检查文件")
                return
            }
            val intent = Intent()
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.action = Intent.ACTION_VIEW
            val type = getMIMEType(file)
            //设置intent的data和Type属性。
            intent.setDataAndType(Uri.fromFile(file), type)
            context.startActivity(intent)
        } catch (e: Exception) {
            LogUtil.e("打开文件失败", e)
        }
    }

    private val MIME_MapTable = arrayOf(arrayOf(".3gp", "video/3gpp"),
            arrayOf(".apk", "application/vnd.android.package-archive"),
            arrayOf(".asf", "video/x-ms-asf"), arrayOf(".avi", "video/x-msvideo"),
            arrayOf(".bin", "application/octet-stream"), arrayOf(".bmp", "image/bmp"),
            arrayOf(".c", "text/plain"), arrayOf(".class", "application/octet-stream"),
            arrayOf(".conf", "text/plain"), arrayOf(".cpp", "text/plain"),
            arrayOf(".doc", "application/msword"), arrayOf(".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
            arrayOf(".xls", "application/vnd.ms-excel"), arrayOf(".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
            arrayOf(".exe", "application/octet-stream"), arrayOf(".gif", "image/gif"),
            arrayOf(".gtar", "application/x-gtar"), arrayOf(".gz", "application/x-gzip"),
            arrayOf(".h", "text/plain"), arrayOf(".htm", "text/html"), arrayOf(".html", "text/html"),
            arrayOf(".jar", "application/java-archive"), arrayOf(".java", "text/plain"), arrayOf(".jpeg", "image/jpeg"),
            arrayOf(".jpg", "image/jpeg"), arrayOf(".js", "application/x-javascript"), arrayOf(".log", "text/plain"),
            arrayOf(".m3u", "audio/x-mpegurl"), arrayOf(".m4a", "audio/mp4a-latm"), arrayOf(".m4b", "audio/mp4a-latm"),
            arrayOf(".m4p", "audio/mp4a-latm"), arrayOf(".m4u", "video/vnd.mpegurl"), arrayOf(".m4v", "video/x-m4v"),
            arrayOf(".mov", "video/quicktime"), arrayOf(".mp2", "audio/x-mpeg"), arrayOf(".mp3", "audio/x-mpeg"),
            arrayOf(".mp4", "video/mp4"), arrayOf(".mpc", "application/vnd.mpohun.certificate"), arrayOf(".mpe", "video/mpeg"),
            arrayOf(".mpeg", "video/mpeg"), arrayOf(".mpg", "video/mpeg"), arrayOf(".mpg4", "video/mp4"),
            arrayOf(".mpga", "audio/mpeg"), arrayOf(".msg", "application/vnd.ms-outlook"), arrayOf(".ogg", "audio/ogg"),
            arrayOf(".pdf", "application/pdf"), arrayOf(".png", "image/png"), arrayOf(".pps", "application/vnd.ms-powerpoint"),
            arrayOf(".ppt", "application/vnd.ms-powerpoint"), arrayOf(".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"),
            arrayOf(".prop", "text/plain"), arrayOf(".rc", "text/plain"), arrayOf(".rmvb", "audio/x-pn-realaudio"),
            arrayOf(".rtf", "application/rtf"), arrayOf(".sh", "text/plain"), arrayOf(".tar", "application/x-tar"),
            arrayOf(".tgz", "application/x-compressed"), arrayOf(".txt", "text/plain"), arrayOf(".wav", "audio/x-wav"),
            arrayOf(".wma", "audio/x-ms-wma"), arrayOf(".wmv", "audio/x-ms-wmv"), arrayOf(".wps", "application/vnd.ms-works"),
            arrayOf(".xml", "text/plain"), arrayOf(".z", "application/x-compress"), arrayOf(".zip", "application/x-zip-compressed"),
            arrayOf("", "*/*"))


    /**
     * 是上传文件
     */
    fun uploadFile(path: String) {

    }
}