package com.chintansoni.android.repositorypattern.di

import com.chintansoni.android.repositorypattern.BuildConfig
import com.chintansoni.android.repositorypattern.model.remote.ApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

val networkModule = module {

    // Dependency: HttpLoggingInterceptor
    single<Interceptor> {
        HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Timber.tag("OkHttp").d(message) }).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    // Dependency: OkHttpClient
    single {
        OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(get())
                .build()
    }

    // Dependency: Retrofit
    single {
        Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(get())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    // Dependency: ApiService
    single {
        val retrofit: Retrofit = get()
        retrofit.create(ApiService::class.java)
    }
}