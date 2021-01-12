package com.voronin.tinkoffmap.di.module

import android.app.Application
import android.content.Context
import com.voronin.tinkoffmap.TinkoffMapApplication
import dagger.Module
import dagger.Provides

@Module
open class ApplicationModule {

    @Provides
    fun provideContext(app: TinkoffMapApplication): Context {
        return app.applicationContext
    }

    @Provides
    fun provideApplication(app: TinkoffMapApplication): Application {
        return app
    }
}
