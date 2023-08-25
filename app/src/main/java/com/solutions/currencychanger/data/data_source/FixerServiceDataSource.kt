package com.solutions.currencychanger.data.data_source

import com.solutions.currencychanger.data.repo.FixerApi
import javax.inject.Inject

class FixerServiceDataSource @Inject constructor (private val fixerRepo:FixerApi) {

    suspend fun getLatestRates() = fixerRepo.getLatestRates()

}