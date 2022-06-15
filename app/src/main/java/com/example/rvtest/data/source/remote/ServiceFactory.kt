package com.example.rvtest.data.source.remote

import com.example.rvtest.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceFactory {

    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level =
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                    else HttpLoggingInterceptor.Level.NONE
            })
            .build()
    }

    fun createApiService(baseUrl: String): ApiService = with(Retrofit.Builder()) {
        baseUrl(baseUrl)
        client(createOkHttpClient())
        addConverterFactory(GsonConverterFactory.create())
        build()
    }.create(ApiService::class.java)
}