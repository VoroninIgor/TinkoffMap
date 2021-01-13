package com.voronin.tinkoff.di.module

import android.app.Application
import android.content.Context
import com.voronin.tinkoff.TinkoffApplication
import com.voronin.tinkoff.utils.list.DiffUtilItemCallbackFactory
import dagger.Module
import dagger.Provides

@Module
open class ApplicationModule {

    @Provides
    fun provideContext(app: TinkoffApplication): Context {
        return app.applicationContext
    }

    @Provides
    fun provideDiffUtilItemCallbackFactory(): DiffUtilItemCallbackFactory {
        return DiffUtilItemCallbackFactory()
    }

    @Provides
    fun provideApplication(app: TinkoffApplication): Application {
        return app
    }
}
