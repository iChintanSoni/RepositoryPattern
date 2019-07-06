package com.chintansoni.android.repositorypattern.di

import androidx.room.Room
import com.chintansoni.android.repositorypattern.model.local.AppDatabase
import com.chintansoni.android.repositorypattern.model.local.DatabaseConstants
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {

    // Dependency: AppDatabase
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, DatabaseConstants.mName).build()
    }

    // Dependency:
    single {
        val appDatabase: AppDatabase = get()
        appDatabase.userDao()
    }
}