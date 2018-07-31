package com.chintansoni.android.repositorypattern.di.component

import android.app.Application
import com.chintansoni.android.repositorypattern.di.module.ActivityModule
import com.chintansoni.android.repositorypattern.di.module.AppModule
import com.chintansoni.android.repositorypattern.di.module.FragmentModule
import com.chintansoni.android.repositorypattern.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ActivityModule::class,
    FragmentModule::class,
    ViewModelModule::class
])
interface AppComponent : AndroidInjector<DaggerApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}