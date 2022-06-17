package com.example.rvtest.data.repository

import android.content.Context
import com.example.rvtest.R
import com.example.rvtest.data.source.local.datastore.DataStoreService
import com.example.rvtest.data.source.local.db.PrayerDao
import com.example.rvtest.data.source.remote.ApiService
import com.example.rvtest.domain.Resource
import com.example.rvtest.extension.toPrayer
import com.example.rvtest.extension.toPrayerEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PrayerRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiService: ApiService,
    private val dao: PrayerDao,
    private val dataStoreService: DataStoreService
) {

    fun getPrayer(zoneId: Int) = Resource.networkBoundResource(
        query = { dao.getPrayerByZoneId(zoneId).map { it?.toPrayer() } },
        fetch = { apiService.getPrayerTime(zoneId) },
        saveFetchResult = { response ->
            dao.deletePrayerByZoneId(response.zoneId)
            dao.insertPrayer(response.toPrayerEntity())
        },
        onFetchFailed = { context.getString(R.string.get_data_error) }
    )

    suspend fun setZoneId(zoneId: Int) = dataStoreService.setZoneId(zoneId)

    fun getCurrentZoneId() = dataStoreService.getZoneId()
}