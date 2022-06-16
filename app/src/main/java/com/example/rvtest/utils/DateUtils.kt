package com.example.rvtest.utils

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    val dateResponseFormat = SimpleDateFormat("dd-MM-yyyy")

    fun parseResponseDate(date: String): Date? {
        return dateResponseFormat.parse(date)
    }

    fun isResponseDateToday(date: String): Boolean {
        return parseResponseDate(date)?.let { DateUtils.isToday(it.time) } ?: true
    }
}