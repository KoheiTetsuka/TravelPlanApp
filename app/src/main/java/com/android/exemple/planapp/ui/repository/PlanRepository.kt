package com.android.exemple.planapp.ui.repository

import com.android.exemple.planapp.db.entities.Plan
import kotlinx.coroutines.flow.Flow

interface PlanRepository {
    fun getAll(): Flow<MutableList<Plan>>

    suspend fun getById(planId: Int): Plan

    suspend fun insertPlan(plan: Plan)

    suspend fun updatePlan(plan: Plan)

    suspend fun deletePlan(plan: Plan)
}