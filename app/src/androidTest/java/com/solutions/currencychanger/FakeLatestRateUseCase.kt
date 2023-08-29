package com.solutions.currencychanger

import com.solutions.currencychanger.data.models.CurrencyModel

class FakeLatestRateUseCase {

    fun calculateFromAmount(apiBase:String, baseCurrency: CurrencyModel, toCurrency: CurrencyModel, amount: Double): Double {
        return if(toCurrency.label != baseCurrency.label)
            if(apiBase == toCurrency.label){
                (amount * (baseCurrency.rate ))
            }else{
                (amount/(toCurrency.rate))
            }
        else{
            amount
        }
    }

    fun calculateToAmount(apiBase:String, baseCurrency: CurrencyModel, toCurrency: CurrencyModel, amount:Double): Double {
        return if(toCurrency.label != baseCurrency.label)
            if(apiBase == baseCurrency.label){
                (amount * (toCurrency.rate ))
            }else{
                (amount / (baseCurrency.rate))
            }
        else{
            amount
        }

    }
}