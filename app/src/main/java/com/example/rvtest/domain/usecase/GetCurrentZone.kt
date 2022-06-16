package com.example.rvtest.domain.usecase

import com.example.rvtest.data.repository.PrayerRepository
import com.example.rvtest.domain.model.Zone
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCurrentZone @Inject constructor(
    private val prayerRepository: PrayerRepository
) {

    operator fun invoke() = prayerRepository.getCurrentZoneId().map { zoneId ->
        Zone.getList().find { it.id == zoneId } ?: Zone.getList().first()
    }
}