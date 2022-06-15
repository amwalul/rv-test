package com.example.rvtest.di

import android.app.Application
import com.example.rvtest.data.source.local.datastore.DataStoreService
import com.example.rvtest.data.source.local.datastore.DataStoreServiceImpl
import com.example.rvtest.data.source.local.db.PrayerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun providePrayerDatabase(application: Application) = PrayerDatabase.create(application)

    @Provides
    @Singleton
    fun providePrayerDao(database: PrayerDatabase) = database.dao

    @Provides
    @Singleton
    fun provideDataStoreService(application: Application): DataStoreService =
        DataStoreServiceImpl(application)
}