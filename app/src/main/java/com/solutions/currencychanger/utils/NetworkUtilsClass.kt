package com.solutions.currencychanger.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo




object NetworkUtilsClass {

    fun isNetworkAvailable(context: Context?): Boolean {
        if(context == null)
            return false
        val connectivityManager: ConnectivityManager? = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        val activeNetworkInfo: NetworkInfo? = connectivityManager?.activeNetworkInfo
        return activeNetworkInfo?.isConnected ?: false
    }
}