package com.chintansoni.android.repositorypattern.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.chintansoni.android.repositorypattern.model.Resource
import com.chintansoni.android.repositorypattern.model.UserRepository
import com.chintansoni.android.repositorypattern.model.local.entity.User
import io.reactivex.schedulers.Schedulers

class ListViewModel constructor(private var userRepository: UserRepository) : ViewModel() {

    private var userLiveData: LiveData<Resource<List<User>>> = LiveDataReactiveStreams.fromPublisher(
        userRepository.getFlowableResourceListUser()
            .subscribeOn(Schedulers.io())
    )

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
