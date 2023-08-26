package com.solutions.currencychanger.data.repo

import com.solutions.currencychanger.data.models.LatestRatesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FixerApi {

    @GET("latest")
    suspend fun getLatestRates(@Query("base") base: String): LatestRatesResponse
}