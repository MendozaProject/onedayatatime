package com.example.onedayatatime.ui

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
class Dates {
    companion object {
        val sqlDateFormat = "yyyy-MM-dd"

        fun dateToSqlDate(date: Date): String {
            return SimpleDateFormat(sqlDateFormat).format(date)
        }

        fun sqlDateToDate(sqlDate: String): Date? {
            return SimpleDateFormat(sqlDateFormat).parse(sqlDate)
        }
    }
}

class Strings {
    companion object {
        fun isNullOrEmpty(string: String?): Boolean {
            return string == null || string.isEmpty()
        }

        fun isNotNullOrEmpty(string: String?): Boolean {
            return string != null && string.isNotEmpty()
        }
    }
}