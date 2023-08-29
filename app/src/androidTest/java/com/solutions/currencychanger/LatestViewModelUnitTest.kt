package com.solutions.currencychanger

import com.google.gson.Gson
import com.solutions.currencychanger.data.models.CurrencyModel
import com.solutions.currencychanger.data.models.LatestRatesResponse
import com.solutions.currencychanger.presentation.LatestRatesUseCase
import com.solutions.currencychanger.ui.changer.LatestRatesViewModel
import com.solutions.currencychanger.wrapper.ResultWrapper
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class LatestViewModelUnitTest {

    @Mock
    lateinit var latestUseCase: LatestRatesUseCase
    private lateinit var viewModel:LatestRatesViewModel

    private val mFakeUseCase = FakeLatestRateUseCase()


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = LatestRatesViewModel(latestUseCase)
    }


    @Test
    fun testConvertFromAnotherToBaseCurrency() {
        val fromCurrency = CurrencyModel("EUR", 1.0)
        val toCurrency = CurrencyModel("EGP", 33.68)
        val amount = mFakeUseCase.calculateFromAmount("EUR" , fromCurrency,toCurrency , 10.0)
        Assert.assertEquals(amount, 0.2969,0.0002)
    }

    @Test
    fun testConvertFromBaseCurrencyToAnother() {
        val fromCurrency = CurrencyModel("EUR", 1.0)
        val toCurrency = CurrencyModel("EGP", 33.68)
        val amount = mFakeUseCase.calculateToAmount("EUR" , fromCurrency,toCurrency , 10.0)
        Assert.assertEquals(amount, 336.8,0.0002)
    }
    @Test
    fun convertForSameCurrency(){
        val fromCurrency = CurrencyModel("EUR", 1.0)
        val toCurrency = CurrencyModel("EUR", 1.0)
        val amount = mFakeUseCase.calculateToAmount("EUR" , fromCurrency,toCurrency , 10.0)
        Assert.assertEquals(amount, 10.0,0.0)
    }
    @Test
    fun testApiCall() : Unit = runBlocking {
        val mockResponse:LatestRatesResponse = Gson().fromJson(MockedDataClass.getLatestRatesJSONResponse() , LatestRatesResponse::class.java)
        Mockito.`when`(latestUseCase.getLatestRates(false,"EUR")).thenReturn(flowOf(ResultWrapper.Success(mockResponse)))
        val resultFlow = viewModel.getLatestRates(true,"EUR")
        val job = async {
            resultFlow.collect {
                Assert.assertEquals(it.data?.base , "EUR")
            }
        }
        job.cancel()
    }


}