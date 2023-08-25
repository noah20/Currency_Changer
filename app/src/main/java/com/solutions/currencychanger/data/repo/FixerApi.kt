package com.solutions.currencychanger.data.repo

import com.solutions.currencychanger.data.models.LatestRatesResponse
import retrofit2.http.GET

interface FixerApi {

    @GET("latest")
    suspend fun getLatestRates(): LatestRatesResponse
}