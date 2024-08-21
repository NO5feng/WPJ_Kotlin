package com.example.wpj_kotlin.utils

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.YearMonth
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.concurrent.getOrSet

private val threadLocalCalendar = ThreadLocal<Calendar>()
val calendar = threadLocalCalendar.getOrSet { Calendar.getInstance() }
object DateTimeUtils {

    private val years = listOf(
        "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027",
        "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035",
    )

    private val months = listOf(
        "1", "2", "3", "4", "5", "6",
        "7", "8", "9", "10", "11", "12"
    )

    private val chinese_year_month_and_day = listOf(
        "年", "月", "日"
    )

    private val remind_str = listOf(
        "当天", "提前一天", "提前两天", "提前一周", "提前一个月"
    )

    fun getYearsList() = years
    fun getMonthsList() = months
    fun getChineseYearMonthAndDayList() = chinese_year_month_and_day
    fun getRemindList() = remind_str

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDaysList(year: Int, month: Int): List<String> {
        val yearMonth = YearMonth.of(year, month)
        val daysInMonth = yearMonth.lengthOfMonth()
        return (1..daysInMonth).map { it.toString() }
    }

    fun formatDate(year: Int, month: Int, day: Int): String {
        val monthStr = if (month < 10) "0$month" else "$month"
        val dayStr = if (day < 10) "0$day" else "$day"
        return "$year-$monthStr-$dayStr"
    }

    @SuppressLint("SimpleDateFormat")
    fun getCurrentTime(isFormat: Boolean = true): String {
        val currentDate = Date()
        if (isFormat) {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            return dateFormat.format(currentDate)
        } else {
            return currentDate.time.toString()
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun disassembleDateFormat(dateString: String): Triple<Int, Int, Int> {
        val format = SimpleDateFormat("yyyy-MM-dd")
        val date = format.parse(dateString)
        val calendar = Calendar.getInstance()
        calendar.time = date

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        return Triple(year, month, day)
    }

    fun getExpiredDate(birthDate: Long, delayed: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val originalDate = Date(birthDate * 1000)
        val calendar = Calendar.getInstance()
        calendar.time = originalDate

        val pattern = "(\\d+)([年月日])".toRegex()
        val matchResult = pattern.find(delayed)

        if (matchResult != null) {
            val (numberStr, unit) = matchResult.destructured
            val number = numberStr.toInt()

            when (unit) {
                "年" -> calendar.add(Calendar.YEAR, number)
                "月" -> calendar.add(Calendar.MONTH, number)
                "日" -> calendar.add(Calendar.DAY_OF_YEAR, number)
                else -> return "Invalid unit"
            }

            return dateFormat.format(calendar.time)
        } else {
            return "Invalid input format"
        }
    }

    fun getRemindDate(date: Long, type: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val originalDate = Date(date * 1000)
        val calendar = Calendar.getInstance()
        calendar.time = originalDate
        when (type) {
            "当天" -> calendar.add(Calendar.YEAR, 0)
            "提前一天" -> calendar.add(Calendar.DAY_OF_YEAR, -1)
            "提前两天" -> calendar.add(Calendar.DAY_OF_YEAR, -2)
            "提前一周" -> calendar.add(Calendar.DAY_OF_YEAR, -7)
            "提前一个月" -> calendar.add(Calendar.DAY_OF_YEAR, -30)
        }
        return dateFormat.format(calendar.time)
    }
}

@SuppressLint("SimpleDateFormat")
fun String.switchTimesTamp(): Long {
    val format = SimpleDateFormat("yyyy-MM-dd")
    try {
        val date = format.parse(this)
        return date.time / 1000
    } catch (e: Exception) {
        e.printStackTrace()
        return -1
    }
}

@SuppressLint("SimpleDateFormat")
fun Long.switchStandardDate(): String {
    val date = Date(this * 1000)
    val format = SimpleDateFormat("yyyy-MM-dd")
    return format.format(date)
}

