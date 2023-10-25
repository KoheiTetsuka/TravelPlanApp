package com.android.exemple.planapp.db.dao

import androidx.room.*
import com.android.exemple.planapp.db.entities.Detail
import kotlinx.coroutines.flow.Flow

@Dao
interface DetailDao {

    @Query("SELECT * FROM 'detail' ORDER BY start_time ASC")
    fun getAll(): Flow<MutableList<Detail>>

    @Query("SELECT * FROM 'detail' where plan_id=:planId ORDER BY date ASC ,start_time ASC")
    fun getAllById(planId: Int): Flow<MutableList<Detail>>

    @Query("SELECT * FROM 'detail' where id=:detailId limit 1")
    fun getById(detailId: Int): Flow<Detail>

    @Insert
    suspend fun insertDetail(detail: Detail)

    @Update
    suspend fun updateDetail(detail: Detail)

    @Delete
    suspend fun deleteDetail(detail: Detail)
}