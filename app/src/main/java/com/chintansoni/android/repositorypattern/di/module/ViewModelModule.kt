package com.chintansoni.android.repositorypattern.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.chintansoni.android.repositorypattern.di.mapkey.ViewModelKey
import com.chintansoni.android.repositorypattern.viewmodel.KotlinViewModelFactory
import com.chintansoni.android.repositorypattern.viewmodel.ListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ListViewModel::class)
    abstract fun bindListViewModel(listViewModel: ListViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: KotlinViewModelFactory): ViewModelProvider.Factory
}