package com.android.exemple.planapp.ui.repository

import com.android.exemple.planapp.db.entities.Property
import kotlinx.coroutines.flow.Flow


interface PropertyRepository {

    fun getAll(): Flow<MutableList<Property>>

    fun getAllById(propertyId: Int): Flow<Property>

    fun getAllByPlanId(planId: Int): Flow<MutableList<Property>>

    suspend fun insertProperty(property: Property)

    suspend fun updateProperty(property: Property)

    suspend fun softDeleteProperty(property: Property)

    suspend fun deleteProperty(property: Property)
}