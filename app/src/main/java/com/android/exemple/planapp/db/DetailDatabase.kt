package com.android.exemple.planapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.exemple.planapp.db.converters.TimeConverter
import com.android.exemple.planapp.db.dao.DetailDao
import com.android.exemple.planapp.db.entities.Detail

@Database(entities = [Detail::class], version = 1, exportSchema = false)
@TypeConverters(TimeConverter::class)
abstract class DetailDatabase: RoomDatabase() {
    abstract fun detailDao(): DetailDao
}