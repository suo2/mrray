package www.mrray.cn.module.cp.activity

import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_operate_report.*
import www.mrray.cn.R
import www.mrray.cn.base.BaseActivity

/**
 *@author suo
 *@date 2018/11/6
 *@desc:
 */
class OperateReportActivity : BaseActivity(){
    override fun getLayoutId(): Int {
        return R.layout.activity_operate_report
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Glide.with(this).load("https://img2.woyaogexing.com/2018/08/14/9dc2bb4e96604f6993e46b05ed17915c!600x600.jpeg").into(img)
    }
}