package com.chintansoni.android.repositorypattern.di

import com.chintansoni.android.repositorypattern.model.UserRepository
import com.chintansoni.android.repositorypattern.viewmodel.ListViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {

    // Dependency: ListViewModel
    viewModel {
        ListViewModel(get())
    }

    // Dependency: UserRepository
    single {
        UserRepository(get(), get())
    }
}