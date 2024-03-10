package com.android.exemple.planapp.ui.repository.impl

import com.android.exemple.planapp.db.dao.PropertyDao
import com.android.exemple.planapp.db.entities.Property
import com.android.exemple.planapp.ui.repository.PropertyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class PropertyRepositoryImpl @Inject constructor(
    private val propertyDao: PropertyDao
) : PropertyRepository {
    override fun getAll(): Flow<MutableList<Property>> {
        return propertyDao.getAll().distinctUntilChanged()
    }

    override fun getAllById(propertyId: Int): Flow<Property> {
        return propertyDao.getAllById(propertyId)
    }

    override fun getAllByPlanId(planId: Int): Flow<MutableList<Property>> {
        return propertyDao.getAllByPlanId(planId).distinctUntilChanged()
    }

    override suspend fun insertProperty(property: Property) {
        return propertyDao.insertProperty(property)
    }

    override suspend fun updateProperty(property: Property) {
        return propertyDao.updateProperty(property)
    }

    override suspend fun softDeleteProperty(property: Property) {
        return propertyDao.softDeleteProperty(property)
    }

    override suspend fun deleteProperty(property: Property) {
        return propertyDao.deleteProperty(property)
    }
}