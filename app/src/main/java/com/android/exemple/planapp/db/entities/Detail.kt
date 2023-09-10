package com.android.exemple.planapp.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "detail")
data class Detail(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "date")
    val date: LocalDate?,

    @ColumnInfo(name = "start_time")
    var startTime: LocalTime?,

    @ColumnInfo(name = "end_time")
    var endTime: LocalTime?,

    @ColumnInfo(name = "cost")
    val cost: String,

    @ColumnInfo(name = "url")
    val url: String,

    @ColumnInfo(name = "memo")
    val memo: String,

    @ColumnInfo(name = "plan_id")
    val planId: Int,
)
