package com.example.rvtest.data.source.remote.response


import com.google.gson.annotations.SerializedName

data class PrayerTimeData(
    @SerializedName("date")
    val date: String,
    @SerializedName("hijri")
    val hijri: String,
    @SerializedName("day")
    val day: String,
    @SerializedName("imsak")
    val imsak: String,
    @SerializedName("subuh")
    val subuh: String,
    @SerializedName("syuruk")
    val syuruk: String,
    @SerializedName("zohor")
    val zohor: String,
    @SerializedName("asar")
    val asar: String,
    @SerializedName("maghrib")
    val maghrib: String,
    @SerializedName("isyak")
    val isyak: String
)