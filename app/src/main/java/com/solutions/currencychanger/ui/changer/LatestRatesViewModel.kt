package com.solutions.currencychanger.ui.changer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.solutions.currencychanger.data.models.CurrencyModel
import com.solutions.currencychanger.presentation.LatestRatesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.DecimalFormat
import javax.inject.Inject

@HiltViewModel
class LatestRatesViewModel @Inject constructor(private val latestUseCase:LatestRatesUseCase) : ViewModel() {
    val mBaseCurrencySelected = MutableLiveData<CurrencyModel>()
    val mToCurrencySelected= MutableLiveData<CurrencyModel>()
    val toAmount = MutableLiveData<String>()
    private var mFromAmount:String? = "1"

    private val formatter: DecimalFormat = DecimalFormat("#.####")

    fun getLatestRates(forceUpdate: Boolean = false , base: String) = latestUseCase.getLatestRates(forceUpdate , base)

    fun setBaseCurrency(currency: CurrencyModel) {
        if(mBaseCurrencySelected.value?.label == currency.label)
            return
        mBaseCurrencySelected.value = currency
        if(mToCurrencySelected.value != null)
            toAmount.value = latestUseCase.calculateToAmount(mBaseCurrencySelected.value!! , mToCurrencySelected.value!! , mFromAmount?.toDouble() ?: 1.0 ).toString()

    }
    fun setToCurrency(currency: CurrencyModel) {
        if(mToCurrencySelected.value?.label == currency.label)
            return
        mToCurrencySelected.value = currency
        if(mBaseCurrencySelected.value != null)
            toAmount.value = latestUseCase.calculateToAmount(mBaseCurrencySelected.value!! , mToCurrencySelected.value!! , mFromAmount?.toDouble() ?: 1.0 ).toString()

    }

    fun shouldFetchForCurrency(newCurrency: String) = latestUseCase.shouldFetchFromCurrency( mToCurrencySelected.value ,newCurrency)

    fun shouldFetchToCurrency(newCurrency: String) = latestUseCase.shouldFetchToCurrency(mBaseCurrencySelected.value  ,newCurrency)

    fun getToAmount(amount:String): String {
        return formatter.format(latestUseCase.calculateToAmount(mBaseCurrencySelected.value!!,mToCurrencySelected.value!!,amount.toDouble()))
    }

    fun getFromAmount(amount:String): String {
        return formatter.format(latestUseCase.calculateFromAmount(mBaseCurrencySelected.value!!,mToCurrencySelected.value!!,amount.toDouble()))
    }

    fun setSelectedAmount(amount:String){
        mFromAmount = amount
    }
    fun swapCurrency() {

        mBaseCurrencySelected.value = mToCurrencySelected.value?.also {
            mToCurrencySelected.value = mBaseCurrencySelected.value
        }
        toAmount.value = latestUseCase.calculateToAmount(mBaseCurrencySelected.value!! , mToCurrencySelected.value!! , mFromAmount?.toDouble() ?: 1.0 ).toString()

    }


}