package com.example.rvtest.di

import com.example.rvtest.BuildConfig
import com.example.rvtest.data.source.remote.ApiService
import com.example.rvtest.data.source.remote.ServiceFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService =
        ServiceFactory.createApiService(BuildConfig.PRAYER_BASE_URL)
}