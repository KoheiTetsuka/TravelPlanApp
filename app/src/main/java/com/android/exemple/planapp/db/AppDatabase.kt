package com.android.exemple.planapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.exemple.planapp.db.converters.DateConverter
import com.android.exemple.planapp.db.dao.PlanDao
import com.android.exemple.planapp.db.entities.Plan

@Database(entities = [Plan::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun planDao(): PlanDao
}