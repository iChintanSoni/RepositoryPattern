package com.chintansoni.android.repositorypattern.model

import com.chintansoni.android.repositorypattern.model.local.dao.UserDao
import com.chintansoni.android.repositorypattern.model.local.entity.User
import com.chintansoni.android.repositorypattern.model.remote.ApiService
import com.chintansoni.android.repositorypattern.model.remote.response.RandomUserResponse
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.functions.Function

class UserRepository constructor(private var apiService: ApiService, private var userDao: UserDao) {

    private var pageNumber: Int = 0
    private var networkBoundSource: NetworkBoundResource<List<User>, RandomUserResponse> =
        object : NetworkBoundResource<List<User>, RandomUserResponse>() {
            override fun getRemote(): Single<RandomUserResponse> {
                return apiService.getUsers(pageNumber)
            }

            override fun getLocal(): Single<List<User>> {
                return userDao.getAll()
            }

            override fun saveCallResult(data: List<User>, isForced: Boolean) {
                if (isForced) {
                    userDao.deleteAll()
                }
                userDao.insertAllUsers(data)
            }

            override fun mapper(): Function<RandomUserResponse, List<User>> {
                return EntityMapper.convert()
            }
        }
    private var flowable: Flowable<Resource<List<User>>> =
        Flowable.create(networkBoundSource, BackpressureStrategy.BUFFER)

    fun getFlowableResourceListUser(): Flowable<Resource<List<User>>> {
        return flowable
    }

    fun refresh() {
        networkBoundSource.refresh()
    }

    fun fetchUsers(isForced: Boolean) {
        networkBoundSource.fetch(isForced)
    }

    fun getNextPageUsers() {
        pageNumber += 1
        networkBoundSource.getRemoteData(false)
    }
}