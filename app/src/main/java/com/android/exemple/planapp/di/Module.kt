package com.android.exemple.planapp.di

import android.content.Context
import androidx.room.Room
import com.android.exemple.planapp.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object Module {
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, AppDatabase::class.java, "plan_database").build()

    @Provides
    fun provideDao(
        db: AppDatabase
    ) = db.planDao()
}