package com.solutions.currencychanger.data.repo

import com.solutions.currencychanger.data.models.CurrencyHistoricalResponse
import com.solutions.currencychanger.data.models.LatestRatesResponse
import com.solutions.currencychanger.wrapper.ResultWrapper
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FixerChangerRepo @Inject constructor(private val fixerApi: FixerApi) {
    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private var mLatestRatesResponse: LatestRatesResponse? = null

    fun getLatestRates(forceUpdate: Boolean = false, base: String) = flow {
        try {
            emit(ResultWrapper.Loading())
            if (!forceUpdate && mLatestRatesResponse != null && base == mLatestRatesResponse?.base) {
                emit(ResultWrapper.Success(mLatestRatesResponse!!))
            } else {
                val latestRatResponse = fixerApi.getLatestRates(base)
                if (latestRatResponse.success)
                    mLatestRatesResponse = latestRatResponse
                emit(ResultWrapper.Success(latestRatResponse))
            }
        } catch (e: Exception) {
            emit(ResultWrapper.Error(throwable = e))
        }
    }

    fun getBaseCurrency() = mLatestRatesResponse?.base

    fun getHistoricalData(base: String, symbols: String, forDays: List<String>) = flow {
        try {
            emit(ResultWrapper.Loading())
            val deferredResults = mutableListOf<Deferred<CurrencyHistoricalResponse>>()
            val jobList = mutableListOf<Job>()
            forDays.forEach {
                val deferred = coroutineScope.async(Dispatchers.IO) {
                    fixerApi.getCurrencyHistorical(it, base, symbols)
                }
                deferredResults.add(deferred)
                jobList.add(deferred)
            }
            jobList.joinAll()
            val results = deferredResults.map { it.await() }
            val isValid = results.filter { ! it.success }
            if(isValid.isEmpty())
                emit(ResultWrapper.Success(results))
            else{
                val failed = isValid[0]
                emit(ResultWrapper.Error(failed.error.code , java.lang.Exception(failed.error.type)))
            }

        } catch (e: Exception) {
            emit(ResultWrapper.Error(throwable = e))
        }

    }


}