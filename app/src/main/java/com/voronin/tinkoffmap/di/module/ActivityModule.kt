package com.voronin.tinkoffmap.di.module

import com.voronin.tinkoffmap.presentation.mainactivity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [ViewModelModule::class])
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity
}
