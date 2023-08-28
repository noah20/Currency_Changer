package com.solutions.currencychanger.presentation

import com.solutions.currencychanger.data.models.CurrencyHistoricalResponse
import com.solutions.currencychanger.data.models.LatestRatesResponse
import com.solutions.currencychanger.wrapper.ResultWrapper
import kotlinx.coroutines.flow.Flow
import java.util.*

interface HistoricalUseCases {

    fun getSelectedCurrencyHistorical(base:String, symbols:Array<String>, forDays: List<String>):Flow<ResultWrapper<List<CurrencyHistoricalResponse>>>

    fun getCurrentLatestRate(base:String): Flow<ResultWrapper<LatestRatesResponse>>

    fun getHistoricalChartData(historicalList: List<CurrencyHistoricalResponse> , currency:String):List<Pair<Date, Float>>

    fun getDateLabels(data: List<Pair<Date, Float>>): List<String>

    fun getHistoricalDaysList(numberOfDays:Int):List<String>

}