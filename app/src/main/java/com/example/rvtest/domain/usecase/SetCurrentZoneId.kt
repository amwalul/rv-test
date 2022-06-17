package com.example.rvtest.domain.usecase

import com.example.rvtest.data.repository.PrayerRepository
import javax.inject.Inject

class SetCurrentZoneId @Inject constructor(
    private val prayerRepository: PrayerRepository
) {

    suspend operator fun invoke(id: Int) = prayerRepository.setZoneId(id)
}