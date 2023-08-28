package com.solutions.currencychanger.data.models

data class CurrencyHistoricalResponse(
    val base: String,
    val date: String,
    val historical: Boolean,
    val rates: Map<String,Float>,
    val success: Boolean,
    val timestamp: Long,
    val error: ApiError
)