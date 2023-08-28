package com.solutions.currencychanger.domain

import com.solutions.currencychanger.data.repo.FixerChangerRepo
import javax.inject.Inject

class FixerServiceDataSource @Inject constructor (private val remoteData: FixerChangerRepo) {

    fun getLatestRates(forceUpdate:Boolean = false , base:String) = remoteData.getLatestRates(forceUpdate ,base)
    fun getBaseCurrency() = remoteData.getBaseCurrency()


}