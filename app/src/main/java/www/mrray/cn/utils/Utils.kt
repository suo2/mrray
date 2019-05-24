package www.mrray.cn.utils

import android.annotation.SuppressLint
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.math.RoundingMode
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * @author suo
 * @date 2018/10/15
 * @desc: 时间转换工具类
 */

object Utils {

    private val CONSTANTS_STRING_EMPTY = ""

    private val DATE_FORMAT_YYYYMMDD = "yyyy/MM/dd"
    private val DATE_FORMAT_YYYYMMDDHHMMSS = "yyyy-MM-dd HH:MM:SS "
    public val billion = 100000000L
    public val thousand = 10000L
    /**
     * get special time by special rules
     *
     * @param time
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    fun getSpecTime(time: Long): String {
        val sdf = SimpleDateFormat("MM-dd hh:mm")
        return sdf.format(Date(time))
    }

    /**
     * 将时间转换为 yyyy/MM/dd
     *
     * @param time
     * @return
     */
    fun getDateYYYYMMDD(time: Long): String {
        if (time < 0) {
            return CONSTANTS_STRING_EMPTY
        }
        val formatter = SimpleDateFormat(DATE_FORMAT_YYYYMMDD, Locale.getDefault())
        val date = Date(time)
        return formatter.format(date)
    }

    /**
     * 将时间转换为 yyyy/MM/ddHHMMSSS
     *
     * @param time
     * @return
     */
    fun getDateYYYYMMDDHHMM(time: Long): String {
        if (time < 0) {
            return CONSTANTS_STRING_EMPTY
        }
        val formatter = SimpleDateFormat(DATE_FORMAT_YYYYMMDDHHMMSS, Locale.getDefault())
        val date = Date(time)
        return formatter.format(date)
    }


    /**
     * 倒计时
     */
    fun countDown(time: Int): Observable<Int> {
        return Observable.interval(0, 1L, TimeUnit.SECONDS)
                .map { t -> time - Integer.valueOf(t.toString()) }
                .take((time + 1).toLong())
                .observeOn(AndroidSchedulers.mainThread())
    }


    /**
     * 保留小数四舍五入
     *
     * @param value
     * @param scale 保留的小数位数
     * @return
     */
    fun round(value: Double, scale: Int): String {
        val format = NumberFormat.getNumberInstance(Locale.ENGLISH)
        format.maximumFractionDigits = scale
        format.minimumFractionDigits = scale
        format.roundingMode = RoundingMode.HALF_UP
        return format.format(value)
    }

    /**
     * 获取单位进率
     */
    fun getRateUnit(value: Float) : Long{
        //此命名以方便为主
        return if(value < thousand){
            1
        }else if (value > thousand && value< billion){
            thousand
        }else{
            billion
        }
    }

    /**
     * 获取单位
     */
    fun getUnit(value: Float) : String{
        //此命名以方便为主
        return if(value < thousand){
            "(万)"
        }else {
            "(亿)"
        }
    }
}


