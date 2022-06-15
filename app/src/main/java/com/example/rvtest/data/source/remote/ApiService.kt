package com.example.rvtest.data.source.remote

import com.example.rvtest.data.source.remote.response.PrayerResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("prayer-time/{zoneId}")
    suspend fun getPrayerTime(@Path("zoneId") zoneId: Int): PrayerResponse
}