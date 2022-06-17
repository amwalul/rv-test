package com.example.rvtest.page.prayertime

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.rvtest.domain.model.Prayer
import com.example.rvtest.domain.model.PrayerTime
import com.example.rvtest.domain.onFailure
import com.example.rvtest.domain.onLoading
import com.example.rvtest.domain.onSuccess
import com.example.rvtest.domain.usecase.GetCurrentZone
import com.example.rvtest.domain.usecase.GetPrayerByCurrentZoneId
import com.example.rvtest.utils.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PrayerTimeViewModel @Inject constructor(
    getPrayerByCurrentZoneId: GetPrayerByCurrentZoneId,
    getCurrentZone: GetCurrentZone
) : ViewModel() {

    val zoneLiveData = getCurrentZone().asLiveData()

    val prayerResourceLiveData = getPrayerByCurrentZoneId()
        .onEach { resource ->
            resource
                .onSuccess { prayer ->
                    prayer?.let {
                        updateData(prayer)
                    }
                }
                .onLoading { prayer ->
                    prayer?.let {
                        updateData(prayer)
                    }
                }
                .onFailure { data, _ ->
                    updateData(data)
                }

        }
        .asLiveData()

    private val _prayerTimeList = mutableListOf<PrayerTime>()
    val prayerTimeList: List<PrayerTime> get() = _prayerTimeList

    private val _selectedIndexLiveData = MutableLiveData<Int>()
    val selectedIndexLiveData: LiveData<Int> get() = _selectedIndexLiveData

    private fun updateData(prayer: Prayer?) {
        val newPrayerTimeList = prayer?.prayerTimeList.orEmpty()

        setPrayerTimeList(newPrayerTimeList)

        if (selectedIndexLiveData.value == null || selectedIndexLiveData.value == -1) {
            setTodayIndex(newPrayerTimeList)
        } else reselectIndex()
    }

    private fun setTodayIndex(prayerTimeList: List<PrayerTime>) {
        val todayPrayerTimeIndex = prayerTimeList.indexOfFirst { prayerTime ->
            DateUtils.isApiDateToday(prayerTime.date)
        }

        setSelectedIndex(todayPrayerTimeIndex)
    }

    private fun setPrayerTimeList(list: List<PrayerTime>) {
        _prayerTimeList.apply {
            clear()
            addAll(list)
        }
    }

    private fun setSelectedIndex(index: Int) {
        _selectedIndexLiveData.value = index
    }

    private fun reselectIndex() {
        _selectedIndexLiveData.value = selectedIndexLiveData.value
    }

    fun incrementIndex() {
        _selectedIndexLiveData.value = selectedIndexLiveData.value?.let { it + 1 }
    }

    fun decrementIndex() {
        _selectedIndexLiveData.value = selectedIndexLiveData.value?.let { it - 1 }
    }
}