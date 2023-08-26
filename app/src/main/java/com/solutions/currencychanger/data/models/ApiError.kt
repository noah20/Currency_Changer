package com.solutions.currencychanger.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ApiError(
    val code: Int,
    val info: String,
    val type: String
) : Parcelable