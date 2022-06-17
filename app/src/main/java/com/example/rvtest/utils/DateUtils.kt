package com.example.rvtest.utils

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    fun parseApiDate(dateString: String): Date? {
        val format = SimpleDateFormat("dd-MM-yyyy")
        return format.parse(dateString)
    }

    fun isApiDateToday(dateString: String): Boolean {
        return parseApiDate(dateString)?.let { DateUtils.isToday(it.time) } ?: true
    }

    fun formatDate(date: Date): String {
        val dateFormat = SimpleDateFormat("dd MMMM yyyy")
        return dateFormat.format(date)
    }
}