package com.solutions.currencychanger.domain

import com.solutions.currencychanger.data.models.LatestRatesResponse
import com.solutions.currencychanger.data.repo.FixerApi
import com.solutions.currencychanger.wrapper.ResultWrapper
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FixerServiceDataSource @Inject constructor (private val fixerRepo:FixerApi) {

    private var mLatestRatesResponse:LatestRatesResponse? = null

    fun getLatestRates(forceUpdate:Boolean = false , base:String) = flow {
        try {
            emit(ResultWrapper.Loading())
            if(!forceUpdate && mLatestRatesResponse != null){
                emit(ResultWrapper.Success(mLatestRatesResponse!!))
            }else{
                val latestRatResponse = fixerRepo.getLatestRates(base)
                if(latestRatResponse.success)
                    mLatestRatesResponse = latestRatResponse
                emit(ResultWrapper.Success(latestRatResponse))
            }

        }catch (e:Exception){
            emit(ResultWrapper.Error(throwable = e))
        }

    }

    fun getBaseCurrency() = mLatestRatesResponse?.base


}