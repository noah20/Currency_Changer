package com.solutions.currencychanger.di

import android.content.Context
import com.solutions.currencychanger.BuildConfig
import com.solutions.currencychanger.R
import com.solutions.currencychanger.utils.NetworkUtilsClass
import com.solutions.currencychanger.wrapper.ExceptionCode.EXCEPTION_CODE_NO_INTERNET
import com.solutions.currencychanger.wrapper.ExceptionCode.GENERAL_ERROR_CODE
import com.solutions.currencychanger.wrapper.NetworkException
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

private const val KEY_ACCESS_KEY = "access_key"
class LoggingInterceptor(context: Context) : Interceptor {
    private val weakContext = context

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val currentRequest = request.url
        val newRequest = currentRequest.newBuilder().addQueryParameter(KEY_ACCESS_KEY, BuildConfig.APIKey).build()
        val newBuilder = request.newBuilder().url(newRequest)

        if (! NetworkUtilsClass.isNetworkAvailable(weakContext)) {
            throw NetworkException(EXCEPTION_CODE_NO_INTERNET , weakContext.getString(R.string.str_no_connection))
        }
        val response:Response
        try {
            response = chain.proceed(newBuilder.build())
            if (! response.isSuccessful) {
                throw NetworkException(response.code , weakContext.getString(R.string.str_general_error))
            }

        }catch (e:Exception){
            throw NetworkException(GENERAL_ERROR_CODE , weakContext.getString(R.string.str_general_error) , e)
        }
        return response
    }


}