package com.chintansoni.android.repositorypattern.model

/**
 * Created by chintan.soni on 23/02/18.
 */
class Resource<out T>(val status: Status, val data: T?, val exception: Exception?) {

    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(exception: Exception?): Resource<T> {
            return Resource(Status.ERROR, null, exception)
        }

        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING, null, null)
        }
    }
}
