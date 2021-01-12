package com.voronin.tinkoffmap.di.component

import com.voronin.tinkoffmap.TinkoffMapApplication
import com.voronin.tinkoffmap.di.module.ActivityModule
import com.voronin.tinkoffmap.di.module.ApiServiceModule
import com.voronin.tinkoffmap.di.module.ApplicationModule
import com.voronin.tinkoffmap.di.module.FragmentModule
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
        ActivityModule::class,
        FragmentModule::class,
    ]
)
interface ApplicationComponent : AndroidInjector<TinkoffMapApplication> {

    fun inject(dialogComponent: DialogComponent)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: TinkoffMapApplication): ApplicationComponent
    }
}
