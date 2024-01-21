package com.android.exemple.planapp.ui.repository.impl

import com.android.exemple.planapp.db.dao.PlanDao
import com.android.exemple.planapp.db.entities.Plan
import com.android.exemple.planapp.ui.repository.PlanRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class PlanRepositoryImpl @Inject constructor(
    private val planDao: PlanDao
) : PlanRepository {
    override fun getAll(): Flow<MutableList<Plan>> {
        return planDao.getAll().distinctUntilChanged()
    }

    override suspend fun getById(planId: Int): Plan {
        return planDao.getById(planId).first()
    }

    override suspend fun insertPlan(plan: Plan) {
        return planDao.insertPlan(plan)
    }

    override suspend fun updatePlan(plan: Plan) {
        return planDao.updatePlan(plan)
    }

    override suspend fun deletePlan(plan: Plan) {
        return planDao.deletePlan(plan)
    }
}