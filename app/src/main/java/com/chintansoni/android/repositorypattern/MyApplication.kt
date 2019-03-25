package com.chintansoni.android.repositorypattern

import android.app.Application
import com.chintansoni.android.repositorypattern.di.databaseModule
import com.chintansoni.android.repositorypattern.di.networkModule
import com.chintansoni.android.repositorypattern.di.viewModelModule
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initializeTimber()
        initializeKoin()
    }

    private fun initializeKoin() {
        startKoin(this, listOf(networkModule, databaseModule, viewModelModule))
    }

    private fun initializeTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}