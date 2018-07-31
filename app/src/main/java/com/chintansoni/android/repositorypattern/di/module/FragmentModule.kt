package com.chintansoni.android.repositorypattern.di.module

import com.chintansoni.android.repositorypattern.ui.list.ListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    internal abstract fun contributeListFragment(): ListFragment

}