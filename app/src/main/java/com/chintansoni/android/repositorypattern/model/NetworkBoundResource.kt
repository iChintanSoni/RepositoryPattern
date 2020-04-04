package com.chintansoni.android.repositorypattern.model

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Deferred

abstract class NetworkBoundResource<LocalType, RemoteType> {

    private val mutableLiveData = MutableLiveData<Resource<LocalType>>()

    abstract suspend fun getRemoteAsync(): RemoteType

    abstract suspend fun getLocal(): LocalType

    abstract suspend fun saveCallResult(data: LocalType, isForced: Boolean)

    abstract suspend fun mapper(remoteType: RemoteType): LocalType

    suspend fun refresh() {
        getRemoteData(true)
    }

    suspend fun fetch(isForced: Boolean) {
        try {
            mutableLiveData.postValue(Resource.Success(getLocal()))
            val remoteData = getRemoteAsync()
            saveCallResult(mapper(remoteData), isForced)
            mutableLiveData.postValue(Resource.Success(getLocal()))
        } catch (exception: Exception) {
            mutableLiveData.postValue(Resource.Error(exception))
        }
    }

    suspend fun getRemoteData(isForced: Boolean) {
        try {
            mutableLiveData.postValue(Resource.Loading())
            val remoteData = getRemoteAsync()
            saveCallResult(mapper(remoteData), isForced)
            mutableLiveData.postValue(Resource.Success(getLocal()))
        } catch (exception: Exception) {
            mutableLiveData.postValue(Resource.Error(exception))
        }
    }

    fun asLiveData(): MutableLiveData<Resource<LocalType>> {
        return mutableLiveData
    }
}