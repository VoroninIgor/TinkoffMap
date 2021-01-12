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
        private val BASE_URL = ""
    }

    @Provides
    @Singleton
    fun provideApiService(
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
    fun provideGson(): Gson {
        return Gson()
    }
}
