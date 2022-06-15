package com.example.rvtest.data.repository

import com.example.rvtest.data.source.local.datastore.DataStoreService
import com.example.rvtest.data.source.local.db.PrayerDao
import com.example.rvtest.data.source.remote.ApiService
import com.example.rvtest.domain.Resource
import com.example.rvtest.extension.toPrayerEntity
import javax.inject.Inject

class PrayerRepository @Inject constructor(
    private val apiService: ApiService,
    private val dao: PrayerDao,
    private val dataStoreService: DataStoreService
) {

    fun getPrayerTime(zoneId: Int) = Resource.networkBoundResource(
        query = { dao.getPrayerByZoneId(zoneId) },
        fetch = { apiService.getPrayerTime(zoneId) },
        saveFetchResult = { response -> dao.insertPrayer(response.toPrayerEntity()) }
    )

    suspend fun setZoneId(zoneId: Int) = dataStoreService.setZoneId(zoneId)

    fun getCurrentZoneId() = dataStoreService.getZoneId()
}