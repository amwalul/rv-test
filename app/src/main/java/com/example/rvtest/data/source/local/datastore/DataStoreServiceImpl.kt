package com.example.rvtest.data.source.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.rvtest.domain.model.Zone
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreServiceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : DataStoreService {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

    private val keyZoneId = intPreferencesKey("keyZoneId")

    override suspend fun setZoneId(id: Int) {
        context.dataStore.edit { preferences ->
            preferences[keyZoneId] = id
        }
    }

    override fun getZoneId() = context.dataStore.data.map { preferences ->
        preferences[keyZoneId] ?: Zone.getList().first().id
    }
}