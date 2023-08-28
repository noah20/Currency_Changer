package com.solutions.currencychanger.di

import android.content.Context
import com.solutions.currencychanger.BuildConfig
import com.solutions.currencychanger.domain.FixerServiceDataSource
import com.solutions.currencychanger.data.repo.FixerApi
import com.solutions.currencychanger.presentation.LatestRatesUseCase
import com.solutions.currencychanger.presentation.LatestRatesUseCaseImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideHTTPClient(@ApplicationContext context: Context): OkHttpClient {

        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(60L , TimeUnit.SECONDS)
        httpClient.readTimeout(60L , TimeUnit.SECONDS)
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(LoggingInterceptor(context))
        return httpClient.build()

    }

    @Provides
    fun provideRetrofitObject(client: OkHttpClient): Retrofit {

        return Retrofit.Builder().baseUrl(BuildConfig.API_URL)
            .client(client).addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideFixerApiService(retrofit: Retrofit):FixerApi{
        return retrofit.create(FixerApi::class.java)
    }

    @Provides
    fun provideFixerServiceDataSource(dataSource: FixerServiceDataSource): LatestRatesUseCase {
        return LatestRatesUseCaseImp(dataSource)
    }


}