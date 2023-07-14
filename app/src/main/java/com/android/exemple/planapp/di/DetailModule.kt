package com.android.exemple.planapp.di

import android.content.Context
import androidx.room.Room
import com.android.exemple.planapp.db.DetailDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DetailModule {

    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, DetailDatabase::class.java, "detail_database").build()

    @Provides
    fun provideDao(
        db: DetailDatabase
    ) = db.detailDao()
}