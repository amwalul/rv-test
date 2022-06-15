package com.example.rvtest.data.source.local.db

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.rvtest.domain.model.PrayerTime
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class Converter {
    @TypeConverter
    fun fromPrayerTimeListJson(json: String) = Gson().fromJson<List<PrayerTime>>(
        json,
        object : TypeToken<List<PrayerTime>>() {}.type
    )

    @TypeConverter
    fun toPrayerTimeListJson(prayerTimeList: List<PrayerTime>) = Gson().toJson(
        prayerTimeList,
        object : TypeToken<List<PrayerTime>>() {}.type
    ) ?: "[]"
}