package com.example.rvtest.page.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.rvtest.domain.model.Zone
import com.example.rvtest.domain.usecase.GetCurrentZone
import com.example.rvtest.domain.usecase.SetCurrentZoneId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val setCurrentZoneId: SetCurrentZoneId,
    getCurrentZone: GetCurrentZone
) : ViewModel() {

    val currentZoneLiveData = getCurrentZone().asLiveData()

    private var _selectedZone = Zone.getList().first()
    private val selectedZone get() = _selectedZone

    fun setSelectedZone(zone: Zone) {
        _selectedZone = zone
    }

    fun updateZoneId() = viewModelScope.launch {
        setCurrentZoneId(selectedZone.id)
    }
}