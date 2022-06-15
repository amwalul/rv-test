package com.example.rvtest.data.source.local.datastore

import kotlinx.coroutines.flow.Flow

interface DataStoreService {
    suspend fun setZoneId(id: Int)

    fun getZoneId(): Flow<Int>
}