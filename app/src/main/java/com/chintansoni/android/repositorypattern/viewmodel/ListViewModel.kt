package com.chintansoni.android.repositorypattern.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.LiveDataReactiveStreams
import android.arch.lifecycle.ViewModel
import com.chintansoni.android.repositorypattern.model.Resource
import com.chintansoni.android.repositorypattern.model.UserRepository
import com.chintansoni.android.repositorypattern.model.local.entity.User
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class ListViewModel @Inject constructor(private var userRepository: UserRepository) : ViewModel() {

    private var userLiveData: LiveData<Resource<List<User>>> = LiveDataReactiveStreams.fromPublisher(userRepository.getFlowableResourceListUser()
            .subscribeOn(Schedulers.io()))

    fun refreshUsers() {
        userRepository.refresh()
    }

    fun getUsers(): LiveData<Resource<List<User>>> {
        if (userLiveData.value == null) {
            userRepository.fetchUsers(true)
        }
        return userLiveData
    }

    fun getNextPageUsers() {
        userRepository.getNextPageUsers()
    }
}
