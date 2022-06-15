package com.example.rvtest.data.source.remote.response


import com.google.gson.annotations.SerializedName

data class PrayerResponse(
    @SerializedName("prayer_times")
    val prayerTimes: List<PrayerTimeData>,
    @SerializedName("start")
    val start: String,
    @SerializedName("end")
    val end: String,
    @SerializedName("zone")
    val zone: String,
    @SerializedName("zoneId")
    val zoneId: Int,
    @SerializedName("locations")
    val locations: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("source")
    val source: String,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double
)