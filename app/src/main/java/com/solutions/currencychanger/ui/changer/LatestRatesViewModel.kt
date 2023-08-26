package com.solutions.currencychanger.ui.changer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.solutions.currencychanger.data.models.CurrencyModel
import com.solutions.currencychanger.presentation.LatestRatesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.DecimalFormat
import javax.inject.Inject

@HiltViewModel
class LatestRatesViewModel @Inject constructor(private val latestUseCase:LatestRatesUseCase) : ViewModel() {
    private var mBaseCurrencySelected:CurrencyModel? = null
    private var mToCurrencySelected:CurrencyModel? = null
    val fromAmount = MutableLiveData<String>()
    val toAmount = MutableLiveData<String>()
    private var mFromAmount:String? = null

    private val formatter: DecimalFormat = DecimalFormat("#.####")

    fun getLatestRates(forceUpdate: Boolean = false , base: String) = latestUseCase.getLatestRates(forceUpdate , base)

    fun setBaseCurrency(currency: CurrencyModel) {
        if(mBaseCurrencySelected?.label == currency.label)
            return
        mBaseCurrencySelected = currency
        if(mToCurrencySelected != null)
            toAmount.value = latestUseCase.calculateToAmount(mBaseCurrencySelected!! , mToCurrencySelected!! , mFromAmount?.toDouble() ?: 0.0 ).toString()

    }

    fun getFromCurrency() = mBaseCurrencySelected

    fun setToCurrency(currency: CurrencyModel) {
        if(mToCurrencySelected?.label == currency.label)
            return
        mToCurrencySelected = currency
        if(mBaseCurrencySelected != null)
            toAmount.value = latestUseCase.calculateToAmount(mBaseCurrencySelected!! , mToCurrencySelected!! , mFromAmount?.toDouble() ?: 0.0 ).toString()

    }

    fun getToCurrency() = mToCurrencySelected

    fun shouldFetchForCurrency(newCurrency: String) = latestUseCase.shouldFetchFromCurrency( mToCurrencySelected ,newCurrency)

    fun shouldFetchToCurrency(newCurrency: String) = latestUseCase.shouldFetchToCurrency(mBaseCurrencySelected  ,newCurrency)

    fun getToAmount(amount:String): String {
        return formatter.format(latestUseCase.calculateToAmount(mBaseCurrencySelected!!,mToCurrencySelected!!,amount.toDouble()))
    }

    fun getFromAmount(amount:String): String {
        return formatter.format(latestUseCase.calculateFromAmount(mBaseCurrencySelected!!,mToCurrencySelected!!,amount.toDouble()))
    }

    fun setSelectedAmount(amount:String){
        mFromAmount = amount
    }

}