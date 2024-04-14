package com.android.exemple.planapp.ui.repository.impl

import com.android.exemple.planapp.db.dao.DetailDao
import com.android.exemple.planapp.db.entities.Detail
import com.android.exemple.planapp.ui.repository.DetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val detailDao: DetailDao
) : DetailRepository {

    override fun getAllById(planId: Int): Flow<MutableList<Detail>> {
        return detailDao.getAllById(planId)
    }

    override fun getById(detailId: Int): Flow<Detail> {
        return detailDao.getById(detailId)
    }

    override suspend fun insertDetail(detail: Detail) {
        return detailDao.insertDetail(detail)
    }

    override suspend fun updateDetail(detail: Detail) {
        return detailDao.updateDetail(detail)
    }

    override suspend fun deleteDetail(detail: Detail) {
        return detailDao.deleteDetail(detail)
    }

    override suspend fun deleteDetailByPlanId(planId: Int) {
        return detailDao.deleteDetailByPlanId(planId)
    }

}