package com.example.rvtest.domain.usecase

import com.example.rvtest.data.repository.PrayerRepository
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class GetPrayerByCurrentZoneId @Inject constructor(
    private val prayerRepository: PrayerRepository
) {

    operator fun invoke() = prayerRepository.getCurrentZoneId().flatMapLatest { zoneId ->
        prayerRepository.getPrayer(zoneId)
    }
}