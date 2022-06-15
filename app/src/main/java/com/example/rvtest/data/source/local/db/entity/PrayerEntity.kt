package com.example.rvtest.data.source.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rvtest.domain.model.PrayerTime

@Entity
data class PrayerEntity(
    val zoneId: Int,
    val prayerTimeList: List<PrayerTime>,
    @PrimaryKey
    val id: Int? = null,
)
