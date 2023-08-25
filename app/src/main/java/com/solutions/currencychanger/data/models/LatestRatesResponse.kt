package com.solutions.currencychanger.data.models

data class LatestRatesResponse(
    val base: String,
    val date: String,
    val rates: Map<String,String>,
    val success: Boolean,
    val timestamp: Int,
    val error:ApiError
)