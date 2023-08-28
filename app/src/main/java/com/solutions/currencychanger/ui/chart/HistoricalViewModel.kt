package com.solutions.currencychanger.ui.chart

import androidx.lifecycle.ViewModel
import com.solutions.currencychanger.data.models.CurrencyHistoricalResponse
import com.solutions.currencychanger.data.models.LatestRatesResponse
import com.solutions.currencychanger.presentation.HistoricalUseCases
import com.solutions.currencychanger.wrapper.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HistoricalViewModel @Inject constructor(private val useCase: HistoricalUseCases) : ViewModel() {

    fun getHistorical(baseCurrency:String, toCurrencies:Array<String> ): Flow<ResultWrapper<List<CurrencyHistoricalResponse>>> {

        return useCase.getSelectedCurrencyHistorical(baseCurrency , toCurrencies , useCase.getHistoricalDaysList(4))
    }
    
    fun getLatestRateForCurrency(baseCurrency:String): Flow<ResultWrapper<LatestRatesResponse>> {
        return useCase.getCurrentLatestRate(baseCurrency)
    }

    fun getDateLabels(data: List<Pair<Date, Float>>): List<String>  = useCase.getDateLabels(data)

    fun getToCurrencyData(historical: List<CurrencyHistoricalResponse> , currency:String) = useCase.getHistoricalChartData(historical , currency)
    fun getTopCurrency(rates: Map<String, Double>?) = useCase.getTopCurrency(rates)
}