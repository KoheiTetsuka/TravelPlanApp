package com.android.exemple.planapp.db.dao

import androidx.room.*
import com.android.exemple.planapp.db.entities.Plan
import kotlinx.coroutines.flow.Flow

@Dao
interface PlanDao {

    @Query("SELECT * FROM 'plan'")
    fun getAll(): Flow<MutableList<Plan>>

    @Query("SELECT * FROM 'plan' where id=:planId limit 1")
    fun getById(planId: Int): Flow<Plan>

    @Insert
    suspend fun insertPlan(plan: Plan)

    @Update
    suspend fun updatePlan(plan: Plan)

    @Delete
    suspend fun deletePlan(plan: Plan)
}