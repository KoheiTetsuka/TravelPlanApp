package com.android.exemple.planapp.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "property")
data class Property(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "title")
    val title: String = "",

    @ColumnInfo(name = "plan_id")
    val planId: Int = 0,

    @ColumnInfo(name = "delete_flag")
    val deleteFlag: String = "0",
)