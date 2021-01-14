package com.voronin.api.di

import android.content.Context
import com.google.gson.Gson
import com.readystatesoftware.chuck.ChuckInterceptor
import com.voronin.api.TinkoffApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
open class ApiServiceModule {

    companion object {
        private const val BASE_URL = " https://api.tinkoff.ru/v1/"

        private const val CONNECTION_TIMEOUTS_MS = 15000L
    }

    @Provides
    @Singleton
    internal fun provideApiService(
        gson: Gson,
        client: OkHttpClient,
    ): TinkoffApiService = Retrofit
        .Builder()
        .client(client)
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(TinkoffApiService::class.java)

    @Provides
    @Singleton
    fun provideOkHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(CONNECTION_TIMEOUTS_MS, TimeUnit.MILLISECONDS)
            readTimeout(CONNECTION_TIMEOUTS_MS, TimeUnit.MILLISECONDS)
            writeTimeout(CONNECTION_TIMEOUTS_MS, TimeUnit.MILLISECONDS)
            addInterceptor(ChuckInterceptor(context))
        }.build()
    }

    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        return Gson()
    }
}
