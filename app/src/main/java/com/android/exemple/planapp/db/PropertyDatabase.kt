package com.android.exemple.planapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.exemple.planapp.db.dao.PropertyDao
import com.android.exemple.planapp.db.entities.Property

@Database(entities = [Property::class], version = 1, exportSchema = false)
abstract class PropertyDatabase: RoomDatabase() {
    abstract fun propertyDao(): PropertyDao
}