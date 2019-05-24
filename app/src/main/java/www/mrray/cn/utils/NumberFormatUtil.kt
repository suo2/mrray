package www.mrray.cn.utils

import www.mrray.cn.R
import www.mrray.cn.base.MainApplication
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.*

/**
 *@author suo
 *@date 2018/11/10
 *@desc:
 */
object NumberFormatUtil {

    /** 保留整数  */
    val FORMAT_HASHTAG_WITH_ZERO = "###,##0."

    /** 保留2位小数  */
    val FORMAT_HASHTAG_WITH_TWO = "###,##0.00"

    /** 保留3位小数  */
    val FORMAT_HASHTAG_WITH_THREE = "###,##0.000"

    /**
     * isTenThousand 如果为true，表示单位为万的
     */
    fun numberFormatToArray(value: String?,  unit: String = "" , isTenThousand : Boolean = true , decimalPlaces: String = FORMAT_HASHTAG_WITH_TWO): Array<String?> {
        var result: Array<String?> = if(isTenThousand)  numberFormatArrayWan(value,decimalPlaces) else numberFormatArray(value,decimalPlaces)
        result[1] = result[1] + unit
        return result
    }

    //保留两位小数转成String
    fun numberFormatToString(value: String?,  unit: String = "" , isTenThousand : Boolean = true ,decimalPlaces: String = FORMAT_HASHTAG_WITH_TWO): String? {
        var result: Array<String?> = if(isTenThousand)  numberFormatArrayWan(value,decimalPlaces) else numberFormatArray(value,decimalPlaces)
        return result[0] + result[1] +unit
    }


    /**
     * 将数值转化为带“万”或“亿” String[0]为数字 String[1]为单位
     * @param value
     * @return
     */
    private fun numberFormatArray(mValue: String?, decimalPlaces: String  , unit : String = "" ): Array<String?> {
        val result = arrayOfNulls<String>(2)
        if (mValue.isNullOrEmpty()) {
            result[0] = "0"
            result[1] = "" + unit
            return result
        }
        var value = mValue!!.toDouble()
        val billion = 100000000
        val thousand = 10000
        if (value < thousand) {
            result[0] = mValue
            result[1] = "" + unit
            return result
        }
        if (value in thousand..(billion - 1)) {
            val temp = value / thousand
            result[0] = numberFormatBase(BigDecimal(temp.toString()), decimalPlaces)
            result[1] = MainApplication.instance.getString(R.string.thousand) + unit
            return result
        }
        if (value >= billion) {
            val temp = value / billion
            result[0] = numberFormatBase(BigDecimal(temp.toString()), decimalPlaces)
            result[1] = MainApplication.instance.getString(R.string.billion) + unit
            return result
        }
        return result
    }

    /**
     * 将数值转化为带“万”或“亿” String[0]为数字 String[1]为单位（基数单位为万）
     * @param value
     * @return
     */
    private fun numberFormatArrayWan(mValue: String? , decimalPlaces: String , unit : String = ""  ): Array<String?> {
        val result = arrayOfNulls<String>(2)
        if (mValue.isNullOrEmpty()) {
            result[0] = "0"
            result[1] = MainApplication.instance.getString(R.string.thousand) + unit
            return result
        }
        var value = mValue!!.toDouble()
        val billion = 100000000
        val thousand = 10000
        if (value < thousand) {
            result[0] = mValue
            result[1] = MainApplication.instance.getString(R.string.thousand) + unit
            return result
        }
        if (value in thousand..(billion - 1)) {
            val temp = value / thousand
            result[0] = numberFormatBase(BigDecimal(temp.toString()), decimalPlaces)
            result[1] = MainApplication.instance.getString(R.string.billion) + unit
            return result
        }
        return result
    }


    /**
     * 输出数字的格式,如:1,234,567.89
     *
     * @param value  BigDecimal 要格式化的数字
     * @param format String 格式 "###,###.00"
     * @returnString
     */
    private fun numberFormatBase(value: BigDecimal?, format: String): String {
        if (value == null) {
            return "0"
        }
        var characterIndex = -1
        characterIndex = format.indexOf(".")
        var scale = 0
        if (characterIndex > 0) {
            scale = when {
                format.length - characterIndex - 1 == 0 -> 0
                format.length - characterIndex - 1 == 1 -> 1
                format.length - characterIndex - 1 == 3 -> 3
                format.length - characterIndex - 1 == 4 -> 4
                format.length - characterIndex - 1 == 5 -> 5
                else -> 2
            }
        }
        var round = round(value.toDouble(), scale)
        // 不分割，去分隔符
        if (format.indexOf(",") < 0) {
            round = round.replace(",".toRegex(), "")
        }
        return round
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
}