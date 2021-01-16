package com.voronin.tinkoff.di.module

import com.voronin.tinkoff.di.scope.PerActivity
import com.voronin.tinkoff.presentation.mainactivity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [ViewModelModule::class])
abstract class ActivityModule {

    @ContributesAndroidInjector
    @PerActivity
    abstract fun mainActivity(): MainActivity
}
