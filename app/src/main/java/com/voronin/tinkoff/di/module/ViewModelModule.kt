package com.voronin.tinkoff.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.voronin.tinkoff.di.util.ViewModelFactory
import com.voronin.tinkoff.di.util.ViewModelKey
import com.voronin.tinkoff.presentation.depositionpoints.detail.DepositionPointViewModel
import com.voronin.tinkoff.presentation.depositionpoints.list.DepositionPointsListViewModel
import com.voronin.tinkoff.presentation.depositionpoints.map.DepositionPointsMapViewModel
import com.voronin.tinkoff.presentation.depositionpoints.viewer.DepositionPointsViewerViewModel
import com.voronin.tinkoff.presentation.mainactivity.MainActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(DepositionPointViewModel::class)
    abstract fun depositionPointViewModel(viewModel: DepositionPointViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DepositionPointsListViewModel::class)
    abstract fun depositionPointsListViewModel(viewModel: DepositionPointsListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun mainActivityViewModel(viewModel: MainActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DepositionPointsMapViewModel::class)
    abstract fun depositionPointsMapViewModel(viewModel: DepositionPointsMapViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DepositionPointsViewerViewModel::class)
    abstract fun depositionPointsViewerViewModel(viewModel: DepositionPointsViewerViewModel): ViewModel

}
