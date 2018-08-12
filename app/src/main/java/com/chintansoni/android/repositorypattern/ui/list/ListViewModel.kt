package com.chintansoni.android.repositorypattern.ui.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.chintansoni.android.repositorypattern.model.Resource
import com.chintansoni.android.repositorypattern.model.UserRepository
import com.chintansoni.android.repositorypattern.model.local.entity.User
import javax.inject.Inject

class ListViewModel @Inject constructor(private var userRepository: UserRepository) : ViewModel() {

    private var pageNumber: Int = 0

    fun getUsers(isForced: Boolean): LiveData<Resource<List<User>>> {
        return userRepository.getUsers(pageNumber, isForced)
    }

    fun getNextPageUsers() {
        pageNumber += 1
        userRepository.getNextPageUsers(pageNumber)
    }
}
