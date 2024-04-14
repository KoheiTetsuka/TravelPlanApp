package com.android.exemple.planapp.db.dao

import androidx.room.*
import com.android.exemple.planapp.db.entities.Detail
import kotlinx.coroutines.flow.Flow

@Dao
interface DetailDao {

    @Query("SELECT * FROM 'detail' WHERE plan_id=:planId ORDER BY CASE WHEN date IS NULL THEN 1 ELSE 0 END ,date ASC " +
            ",CASE WHEN start_time IS NULL THEN 1 ELSE 0 END ,start_time ASC ,CASE WHEN end_time IS NULL THEN 1 ELSE 0 END ,end_time ASC")
    fun getAllById(planId: Int): Flow<MutableList<Detail>>

    @Query("SELECT * FROM 'detail' WHERE id=:detailId LIMIT 1")
    fun getById(detailId: Int): Flow<Detail>

    @Insert
    suspend fun insertDetail(detail: Detail)

    @Update
    suspend fun updateDetail(detail: Detail)

    @Delete
    suspend fun deleteDetail(detail: Detail)

    @Query("DELETE FROM 'detail' WHERE plan_id=:planId")
    suspend fun deleteDetailByPlanId(planId: Int)
}