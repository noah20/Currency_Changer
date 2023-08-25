package com.solutions.currencychanger.data.models

data class ApiError(
    val code: Int,
    val info: String,
    val type: String
)