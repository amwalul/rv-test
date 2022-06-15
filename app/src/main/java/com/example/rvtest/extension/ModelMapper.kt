package com.example.rvtest.extension

import com.example.rvtest.data.source.local.db.entity.PrayerEntity
import com.example.rvtest.data.source.remote.response.PrayerResponse
import com.example.rvtest.data.source.remote.response.PrayerTimeData
import com.example.rvtest.domain.model.PrayerTime

fun PrayerTimeData.toPrayerTime() = PrayerTime(date, subuh, syuruk, zohor, asar, maghrib, isyak)

fun PrayerResponse.toPrayerEntity() = PrayerEntity(zoneId, prayerTimes.map { it.toPrayerTime() })