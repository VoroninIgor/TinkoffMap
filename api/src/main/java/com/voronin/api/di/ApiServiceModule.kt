package com.voronin.api.di

import com.google.gson.Gson
import com.voronin.api.TinkoffApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
open class ApiServiceModule {

    companion object {
        private const val BASE_URL = " https://api.tinkoff.ru/v1/"
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
    internal fun provideGson(): Gson {
        return Gson()
    }
}
