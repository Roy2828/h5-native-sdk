package com.roy.h5native.ext

/**
 *    desc   :
 *    e-mail : 1391324949@qq.com
 *    date   : 2021/7/21 13:47
 *    author : Roy
 *    version: 1.0
 */

/**
 *  防止Int空指针
 * @receiver Any
 * @param value Int?
 * @return Int
 */
fun Any.defaultInt(value: Int?): Int {
    return value ?: 0
}

//时间格式String 转为 int
fun Any.timeStrToNumber(value: String?): Number {
    var valueInt = 0
    if (!value.isNullOrBlank()) {
        val endIndex: Number? = value.toDoubleOrNull() ?: value.toIntOrNull()
        if (endIndex != null) {
            // 如果end是数字格式的字符串，直接返回整数值
            // 这里可以处理endIndex的值
            return endIndex
        } else {
            // 如果end不是数字格式的字符串，尝试删除 ":00" 并再次转换为整数
            val endIndexWithoutColon: Int? = value.replace(":00", "").toIntOrNull()

            if (endIndexWithoutColon != null) {
                // 这里可以处理endIndexWithoutColon的值
                return endIndexWithoutColon
            }
        }
    }
    return valueInt
}


fun Any.defaultString(value: String?): String {
    return value ?: ""
}

fun Any.defaultStringToInt(value: String?): Int {
    return value?.toIntOrNull() ?: 0
}

fun Any.defaultFloat(value: Float?): Float {
    return value ?: 0.toFloat()
}

fun Any.defaultLong(value: Long?): Long {
    return value ?: 0.toLong()
}

fun Any.defaultDouble(value: Double?): Double {
    return value ?: 0.toDouble()
}

fun Any.defaultBoolean(value: Boolean?): Boolean {
    return value ?: false
}


fun Any.StringToInt(value: String?): Int {
    try {
        return defaultInt(value?.toInt())
    } catch (e: Exception) {
    }
    return 0
}

/**
 * 防止转换空指针
 * @receiver Any
 * @param value String?
 * @return Double
 */
fun Any.StringToDouble(value: String?): Double {
    try {
        return defaultDouble(value?.toDouble())
    } catch (e: Exception) {
    }
    return 0.toDouble()
}

fun Any.StringToLong(value: String?): Long {
    try {
        return defaultLong(value?.toLong())
    } catch (e: Exception) {
    }
    return 0.toLong()
}


fun Any.StringToFloat(value: String?): Float {
    try {
        return defaultFloat(value?.toFloat())
    } catch (e: Exception) {
    }
    return 0.toFloat()
}

fun Any.StringToBoolean(value: String?): Boolean {
    try {
        return defaultBoolean(value?.toBoolean())
    } catch (e: Exception) {
    }
    return false
}