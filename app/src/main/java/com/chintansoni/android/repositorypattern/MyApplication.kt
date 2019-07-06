package com.chintansoni.android.repositorypattern

import android.app.Application
import com.chintansoni.android.repositorypattern.di.databaseModule
import com.chintansoni.android.repositorypattern.di.networkModule
import com.chintansoni.android.repositorypattern.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initializeTimber()
        initializeKoin()
    }

    private fun initializeKoin() {
        startKoin {
            androidContext(this@MyApplication)
            modules(listOf(networkModule, databaseModule, viewModelModule))
        }
    }

    private fun initializeTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}