package com.voronin.tinkoff.di.module

import android.app.Application
import android.content.Context
import com.voronin.tinkoff.TinkoffApplication
import dagger.Module
import dagger.Provides

@Module
open class ApplicationModule {

    @Provides
    fun provideContext(app: TinkoffApplication): Context {
        return app.applicationContext
    }

    @Provides
    fun provideApplication(app: TinkoffApplication): Application {
        return app
    }
}
