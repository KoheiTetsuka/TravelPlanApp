package com.android.exemple.planapp.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "property")
data class Property(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String = "",
)