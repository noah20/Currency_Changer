package com.solutions.currencychanger.presentation

import com.solutions.currencychanger.data.models.CurrencyModel
import com.solutions.currencychanger.data.models.LatestRatesResponse
import com.solutions.currencychanger.wrapper.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface LatestRatesUseCase {

    fun getLatestRates(forceUpdate :Boolean,base:String):Flow<ResultWrapper<LatestRatesResponse>>

    fun calculateFromAmount(baseCurrency: CurrencyModel, toCurrency: CurrencyModel, amount:Double):Double

    fun calculateToAmount(baseCurrency: CurrencyModel, toCurrency: CurrencyModel, amount:Double):Double

    fun getApiBaseCurrency():String?
    fun getActualBaseCurrency(baseCurrency: CurrencyModel, toCurrency: CurrencyModel):String?
    fun getActualToCurrency(baseCurrency: CurrencyModel, toCurrency: CurrencyModel):String?

    fun shouldFetchFromCurrency(toCurrencySelected: CurrencyModel?, newCurrency: String): Boolean

    fun shouldFetchToCurrency(baseCurrencySelected: CurrencyModel?, newCurrency: String): Boolean

}