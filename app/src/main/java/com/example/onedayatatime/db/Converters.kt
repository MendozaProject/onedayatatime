package com.example.onedayatatime.db

import androidx.room.TypeConverter
import com.example.onedayatatime.ui.Dates
import java.util.*

class Converters {
//    @TypeConverter
//    fun fromTimestamp(value: Long?): Date? {
//        return value?.let { Date(it) }
//    }
//
//    @TypeConverter
//    fun dateToTimestamp(date: Date?): Long? {
//        return date?.time
//    }

    @TypeConverter
    fun fromDateString(value: String): Date? {
        return value.let { Dates.sqlDateToDate(it) }
    }

    @TypeConverter
    fun dateToDateString(date: Date?): String? {
        return date?.let { Dates.dateToSqlDate(it) }
    }
}
