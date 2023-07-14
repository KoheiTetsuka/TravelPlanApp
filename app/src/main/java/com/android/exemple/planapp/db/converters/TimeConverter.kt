package com.android.exemple.planapp.db.converters

import androidx.room.TypeConverter
import java.time.LocalTime

class TimeConverter {

    @TypeConverter
    fun toSqlTime(time: LocalTime?): String? {
        time ?: return null
        return time.toString()
    }

    @TypeConverter
    fun fromSqlTime(time: String?): LocalTime? {
        time ?: return null
        return LocalTime.parse(time)
    }
}