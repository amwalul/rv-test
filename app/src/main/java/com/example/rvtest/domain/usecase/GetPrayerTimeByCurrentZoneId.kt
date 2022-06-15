package com.example.rvtest.domain.usecase

import com.example.rvtest.data.repository.PrayerRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPrayerTimeByCurrentZoneId @Inject constructor(
    private val prayerRepository: PrayerRepository
) {

    operator fun invoke() {
        
    }
}