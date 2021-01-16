package com.voronin.tinkoff.di.module

import com.voronin.tinkoff.di.scope.PerFragment
import com.voronin.tinkoff.presentation.depositionpoints.detail.DepositionPointFragment
import com.voronin.tinkoff.presentation.depositionpoints.list.DepositionPointsListFragment
import com.voronin.tinkoff.presentation.depositionpoints.map.DepositionPointsMapFragment
import com.voronin.tinkoff.presentation.depositionpoints.viewer.DepositionPointsViewerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [ViewModelModule::class])
abstract class FragmentModule {

    @ContributesAndroidInjector
    @PerFragment
    abstract fun depositionPointFragment(): DepositionPointFragment

    @ContributesAndroidInjector
    @PerFragment
    abstract fun depositionPointsListFragment(): DepositionPointsListFragment

    @ContributesAndroidInjector
    @PerFragment
    abstract fun depositionPointsMapFragment(): DepositionPointsMapFragment

    @ContributesAndroidInjector
    @PerFragment
    abstract fun depositionPointsViewerFragment(): DepositionPointsViewerFragment
}
