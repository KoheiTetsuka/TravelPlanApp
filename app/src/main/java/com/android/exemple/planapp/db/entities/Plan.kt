package com.android.exemple.planapp.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plan")
data class Plan(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var title: String,
    var description: String,
//    var startDate: Date,
//    var endDate: Date,
)
