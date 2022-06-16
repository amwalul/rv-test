package com.example.rvtest.page.prayertime

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.rvtest.domain.model.PrayerTime
import com.example.rvtest.domain.usecase.GetCurrentZone
import com.example.rvtest.domain.usecase.GetPrayerByCurrentZoneId
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PrayerTimeViewModel @Inject constructor(
    private val getPrayerByCurrentZoneId: GetPrayerByCurrentZoneId,
    private val getCurrentZone: GetCurrentZone
) : ViewModel() {

    val zoneLiveData = getCurrentZone().asLiveData()

    val prayerResourceLiveData = getPrayerByCurrentZoneId().asLiveData()

    private val _prayerTimeList = mutableListOf<PrayerTime>()
    val prayerTimeList: List<PrayerTime> get() = _prayerTimeList

    private val _selectedIndexLiveData = MutableLiveData<Int>()
    val selectedIndexLiveData: LiveData<Int> get() = _selectedIndexLiveData

    fun setPrayerTimeList(list: List<PrayerTime>) {
        _prayerTimeList.apply {
            clear()
            addAll(list)
        }
    }

    fun setSelectedIndex(index: Int) {
        _selectedIndexLiveData.value = index
    }
}