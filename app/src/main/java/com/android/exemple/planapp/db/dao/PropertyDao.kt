package com.android.exemple.planapp.db.dao

import androidx.room.*
import com.android.exemple.planapp.db.entities.Property
import kotlinx.coroutines.flow.Flow

@Dao
interface PropertyDao {

    @Query("SELECT * FROM 'property'")
    fun getAll(): Flow<MutableList<Property>>

    @Insert
    suspend fun insertProperty(property: Property)

    @Update
    suspend fun updateProperty(property: Property)

    @Delete
    suspend fun deleteProperty(property: Property)
}