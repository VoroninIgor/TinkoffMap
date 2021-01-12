package com.voronin.tinkoffmap.di.module

import androidx.lifecycle.ViewModelProvider
import com.voronin.tinkoffmap.di.util.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

//    @Binds
//    @IntoMap
//    @ViewModelKey(SplashViewModel::class)
//    abstract fun splashViewModel(viewModel: SplashViewModel): ViewModel

}
