package com.example.wpj_kotlin.utils

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.YearMonth
import java.util.Calendar
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

    fun getCurrentDateTime(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = Calendar.getInstance().time
        return sdf.format(currentDate)
    }

    @SuppressLint("SimpleDateFormat")
    fun DisassembleDateFormat(dateString: String): Triple<Int, Int, Int> {
        val format = SimpleDateFormat("yyyy-MM-dd")
        val date = format.parse(dateString)
        val calendar = Calendar.getInstance()
        calendar.time = date

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        return Triple(year, month, day)

    }
}

/** ---------------Calendar获取年月日----------------*/
inline fun Calendar.getDate() = this.get(Calendar.DATE)
inline fun Calendar.setDate(date: Int) = this.set(Calendar.DATE, date)
inline fun Calendar.getMonth() = this.get(Calendar.MONTH) + 1
inline fun Calendar.setMonth(month: Int) = this.set(Calendar.MONTH, month - 1)
inline fun Calendar.getYear() = this.get(Calendar.YEAR)
inline fun Calendar.setYear(year: Int) = this.set(Calendar.YEAR, year)
