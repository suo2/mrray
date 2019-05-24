package www.mrray.cn.utils

import android.Manifest
import android.annotation.SuppressLint
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.tbruyelle.rxpermissions2.RxPermissions

object RxPermissionManager {

    @SuppressLint("CheckResult")
    fun getWriteAndReadPermission(fragment: Fragment, allowCallback: () -> Unit, errorCallBack: () -> Unit) {
        RxPermissions(fragment).request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe {
                    if (it) {
                        allowCallback()
                    } else {
                        errorCallBack()
                    }
                }
    }

    @SuppressLint("CheckResult")
    fun getCameraPermission(fragment: Fragment, allowCallback: () -> Unit, errorCallBack: () -> Unit) {
        RxPermissions(fragment).request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA)
                .subscribe {
                    if (it) {
                        allowCallback()
                    } else {
                        errorCallBack()
                    }
                }
    }
    @SuppressLint("CheckResult")
    fun getCameraPermission(activity: AppCompatActivity, allowCallback: () -> Unit, errorCallBack: () -> Unit) {
        RxPermissions(activity).request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA)
                .subscribe {
                    if (it) {
                        allowCallback()
                    } else {
                        errorCallBack()
                    }
                }
    }

    @SuppressLint("CheckResult")
    fun getWriteAndReadPermission(activity: AppCompatActivity, allowCallback: () -> Unit, errorCallBack: () -> Unit) {
        RxPermissions(activity).request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe {
                    if (it) {
                        allowCallback()
                    } else {
                        errorCallBack()
                    }
                }
    }
}