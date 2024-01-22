package com.android.exemple.planapp.ui.repository

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.android.exemple.planapp.db.entities.Detail
import kotlinx.coroutines.flow.Flow

interface DetailRepository {

    fun getAll(): Flow<MutableList<Detail>>

    fun getAllById(planId: Int): Flow<MutableList<Detail>>

    fun getById(detailId: Int): Flow<Detail>

    @Insert
    suspend fun insertDetail(detail: Detail)

    @Update
    suspend fun updateDetail(detail: Detail)

    @Delete
    suspend fun deleteDetail(detail: Detail)
}