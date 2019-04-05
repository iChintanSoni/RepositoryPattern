package com.chintansoni.android.repositorypattern.model

/**
 * Created by chintan.soni on 23/02/18.
 */
sealed class Resource<out T> {

    class Success<out T>(val data: T) : Resource<T>()
    class Error<out T>(throwable: Throwable) : Resource<T>()
    class Loading<out T> : Resource<T>()
}
