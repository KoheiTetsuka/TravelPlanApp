package com.android.exemple.planapp.db.converters

import androidx.room.TypeConverter
import java.time.LocalDate

class DateConverter {

    @TypeConverter
    fun toSqlDate(date: LocalDate?): String? {
        date ?: return null
        return date.toString()
    }

    @TypeConverter
    fun fromSqlDate(date: String?): LocalDate? {
        date ?: return null
        return LocalDate.parse(date)
    }
}