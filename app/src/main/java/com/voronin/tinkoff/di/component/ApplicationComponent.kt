package com.voronin.tinkoff.di.component

import com.voronin.api.di.ApiClientModule
import com.voronin.api.di.ApiServiceModule
import com.voronin.tinkoff.TinkoffApplication
import com.voronin.tinkoff.di.module.ActivityModule
import com.voronin.tinkoff.di.module.ApplicationModule
import com.voronin.tinkoff.di.module.FragmentModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ApplicationModule::class,

        ApiServiceModule::class,
        ApiClientModule::class,

        ActivityModule::class,
        FragmentModule::class,
    ]
)
interface ApplicationComponent : AndroidInjector<TinkoffApplication> {

    fun inject(dialogComponent: DialogComponent)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: TinkoffApplication): ApplicationComponent
    }
}
