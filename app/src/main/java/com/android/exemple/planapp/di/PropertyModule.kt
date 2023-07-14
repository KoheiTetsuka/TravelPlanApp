package com.android.exemple.planapp.di

import android.content.Context
import androidx.room.Room
import com.android.exemple.planapp.db.PropertyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PropertyModule {

    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, PropertyDatabase::class.java, "property_database").build()

    @Provides
    fun provideDao(
        db: PropertyDatabase
    ) = db.propertyDao()
}