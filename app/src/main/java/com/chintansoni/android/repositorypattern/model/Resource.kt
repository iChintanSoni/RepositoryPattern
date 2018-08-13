package com.chintansoni.android.repositorypattern.model

/**
 * Created by chintan.soni on 23/02/18.
 */
class Resource<out T>(val status: Status, val data: T?, val throwable: Throwable?) {

    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(throwable: Throwable?): Resource<T> {
            return Resource(Status.ERROR, null, throwable)
        }

        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING, null, null)
        }
    }

    override fun toString(): String {
        return "Resource(status=$status, data=$data, throwable=$throwable)"
    }
}
