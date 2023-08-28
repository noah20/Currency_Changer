package com.solutions.currencychanger.presentation

import com.solutions.currencychanger.data.models.CurrencyHistoricalResponse
import com.solutions.currencychanger.data.models.LatestRatesResponse
import com.solutions.currencychanger.domain.HistoricalDataSource
import com.solutions.currencychanger.wrapper.ResultWrapper
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class HistoricalUseCasesImp @Inject constructor(private val historicalDataSource: HistoricalDataSource) :
    HistoricalUseCases {

    override fun getSelectedCurrencyHistorical(
        base: String,
        symbols: Array<String>,
        forDays: List<String>
    ): Flow<ResultWrapper<List<CurrencyHistoricalResponse>>> {

        return historicalDataSource.getHistoricalData(
            base,
            symbols.joinToString(separator = ","),
            forDays
        )
    }

    override fun getCurrentLatestRate(base: String): Flow<ResultWrapper<LatestRatesResponse>> {
        return historicalDataSource.getLatestRates(false, base)

    }

    override fun getHistoricalChartData(
        historicalList: List<CurrencyHistoricalResponse>,
        currency: String
    ): List<Pair<Date, Float>> {
        val data = mutableListOf<Pair<Date, Float>>()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        historicalList.forEach {
            data.add(Pair(dateFormat.parse(it.date) ?: Date(), it.rates[currency] ?: 0f))
        }
        return data.reversed()
    }

    override fun getDateLabels(data: List<Pair<Date, Float>>): List<String> {
        val labels = mutableListOf<String>()
        val dateFormat = SimpleDateFormat("MMM dd", Locale.ENGLISH)
        for (pair in data) {
            labels.add(dateFormat.format(pair.first))
        }
        return labels
    }

    override fun getHistoricalDaysList(numberOfDays: Int): List<String> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        val simpleDateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val mutableList = mutableListOf<String>()
        for (i in 1..numberOfDays) {
            calendar.add(Calendar.DAY_OF_YEAR, -1)
            val date = calendar.time
            val dateStr = simpleDateFormatter.format(date.time)
            mutableList.add(dateStr)
        }
        return mutableList
    }

    override fun getTopCurrency(rates: Map<String, Double>?): List<Pair<String, Double>> {
        val top = listOf("USD","GBP","GIP","KWD","BHD" ,"OMR","JOD","KYD","CHF","AED")
        return rates?.filter {
            top.contains(it.key)
        }?.toList() ?: emptyList()
    }

}