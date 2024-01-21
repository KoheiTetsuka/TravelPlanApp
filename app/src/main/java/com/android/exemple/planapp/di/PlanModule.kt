package com.android.exemple.planapp.di

import android.content.Context
import androidx.room.Room
import com.android.exemple.planapp.db.PlanDatabase
import com.android.exemple.planapp.ui.repository.PlanRepository
import com.android.exemple.planapp.ui.repository.impl.PlanRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PlanModule {
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, PlanDatabase::class.java, "plan_database").build()

    @Provides
    fun provideDao(
        db: PlanDatabase
    ) = db.planDao()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class PlanRepositoryModule {
    @Singleton
    @Binds
    abstract fun bindPlanRepository(impl: PlanRepositoryImpl): PlanRepository
}