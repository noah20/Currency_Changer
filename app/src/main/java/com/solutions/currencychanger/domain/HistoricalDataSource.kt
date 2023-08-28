package com.solutions.currencychanger.domain

import com.solutions.currencychanger.data.repo.FixerChangerRepo
import javax.inject.Inject

class HistoricalDataSource @Inject constructor(private val remoteData: FixerChangerRepo) {

    fun getHistoricalData(base:String, symbols:String, forDays: List<String>) = remoteData.getHistoricalData(base , symbols , forDays)

    fun getLatestRates(forceUpdate:Boolean, base:String) = remoteData.getLatestRates(forceUpdate , base)

}