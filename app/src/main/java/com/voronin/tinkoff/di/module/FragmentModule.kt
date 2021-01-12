package com.voronin.tinkoff.di.module

import com.voronin.tinkoff.presentation.depositionpoints.detail.DepositionPointFragment
import com.voronin.tinkoff.presentation.depositionpoints.list.DepositionPointsListFragment
import com.voronin.tinkoff.presentation.depositionpoints.map.DepositionPointsMapFragment
import com.voronin.tinkoff.presentation.depositionpoints.viewer.DepositionPointsViewerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [ViewModelModule::class])
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun depositionPointFragment(): DepositionPointFragment

    @ContributesAndroidInjector
    abstract fun depositionPointsListFragment(): DepositionPointsListFragment

    @ContributesAndroidInjector
    abstract fun depositionPointsMapFragment(): DepositionPointsMapFragment

    @ContributesAndroidInjector
    abstract fun depositionPointsViewerFragment(): DepositionPointsViewerFragment

}
