package com.example.rvtest.data.source.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rvtest.data.source.local.db.entity.PrayerEntity

@Database(entities = [PrayerEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class PrayerDatabase : RoomDatabase() {

    abstract val dao: PrayerDao

    companion object {
        fun create(context: Context) =
            Room.databaseBuilder(context, PrayerDatabase::class.java, "prayer_db")
                .addTypeConverter(Converter()).build()
    }
}