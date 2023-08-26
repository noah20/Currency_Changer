package com.solutions.currencychanger.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LatestRatesResponse(
    val base: String,
    val date: String,
    val rates: Map<String,Double>,
    val success: Boolean,
    val timestamp: Int,
    val error:ApiError
) : Parcelable