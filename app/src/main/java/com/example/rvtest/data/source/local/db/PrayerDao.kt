package com.example.rvtest.data.source.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rvtest.data.source.local.db.entity.PrayerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PrayerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPrayer(prayer: PrayerEntity)

    @Query("DELETE FROM PrayerEntity WHERE zoneId = :zoneId")
    suspend fun deletePrayerByZoneId(zoneId: Int)

    @Query("SELECT * FROM PrayerEntity WHERE zoneId = :zoneId")
    fun getPrayerByZoneId(zoneId: Int): Flow<PrayerEntity?>
}