package com.solutions.currencychanger.presentation

import com.solutions.currencychanger.domain.FixerServiceDataSource
import com.solutions.currencychanger.data.models.CurrencyModel
import javax.inject.Inject

class LatestRatesUseCaseImp @Inject constructor(private val dataSource: FixerServiceDataSource):LatestRatesUseCase {


    override fun getLatestRates(forceUpdate :Boolean ,base:String) = dataSource.getLatestRates(forceUpdate ,  base)

    override fun calculateFromAmount(baseCurrency: CurrencyModel, toCurrency: CurrencyModel, amount: Double): Double {

        return if(toCurrency.label != baseCurrency.label)
            if(getApiBaseCurrency() == toCurrency.label){
                (amount * (toCurrency.rate ))
            }else{
                (amount/(toCurrency.rate))
            }
        else{
            amount
        }
    }

    override fun calculateToAmount(
        baseCurrency: CurrencyModel,
        toCurrency: CurrencyModel, amount:Double): Double {
        return if(toCurrency.label != baseCurrency.label)
            if(getApiBaseCurrency() == baseCurrency.label){
                (amount * (toCurrency.rate ))
            }else{
                (amount / (baseCurrency.rate))
            }
        else{
            amount
        }

    }

    override fun getApiBaseCurrency(): String? {
        return dataSource.getBaseCurrency()
    }

    override fun shouldFetchFromCurrency(
        toCurrencySelected: CurrencyModel?,
        newCurrency: String
    ): Boolean {
        val apiBase = getApiBaseCurrency()

        return !(apiBase == toCurrencySelected?.label || apiBase == newCurrency)
    }

    override fun shouldFetchToCurrency(
        baseCurrencySelected: CurrencyModel?,
        newCurrency: String
    ): Boolean {
        val apiBase = getApiBaseCurrency()
        return !(apiBase == baseCurrencySelected?.label || apiBase == newCurrency)
    }

}