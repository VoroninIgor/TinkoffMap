package com.voronin.api.di

import com.voronin.api.TinkoffApiService
import com.voronin.api.clients.TinkoffApiClient
import com.voronin.api.clients.TinkoffApiClientImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApiClientModule {

    @Provides
    @Singleton
    internal fun provideApiClient(apiService: TinkoffApiService): TinkoffApiClient {
        return TinkoffApiClientImpl(
            apiService
        )
    }
}
