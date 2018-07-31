package com.chintansoni.android.repositorypattern.model

import android.arch.lifecycle.MutableLiveData
import com.chintansoni.android.repositorypattern.model.local.dao.UserDao
import com.chintansoni.android.repositorypattern.model.local.entity.User
import com.chintansoni.android.repositorypattern.model.remote.ApiService
import com.chintansoni.android.repositorypattern.model.remote.response.RandomUserResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private var apiService: ApiService, private var userDao: UserDao) {

    private var users = MutableLiveData<Resource<List<User>>>()
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var pageNumber: Int = 0

    fun getUsers(page: Int, isForced: Boolean): MutableLiveData<Resource<List<User>>> {
        return if (users.value == null) {
            pageNumber = page
            getUsersFromLocalAndRemote(isForced)
            users
        } else {
            if (pageNumber == page) {
                users.value = users.value
            } else {
                pageNumber = page
                getUsersFromLocalAndRemote(isForced)
            }
            users
        }
    }

    fun getNextPageUsers(page: Int) {
        if (pageNumber == page) {
            users.value = users.value
        } else {
            pageNumber = page
            getUsersFromRemote()
        }
    }

    private fun getUsersFromRemote() {
        users.value = Resource.loading()
        val disposable = getFromNetwork(pageNumber)
                .flatMap {
                    return@flatMap insertIntoDatabase(false, it)
                }
                .flatMap {
                    return@flatMap getFromDatabase()
                }
                .subscribe({
                    users.value = Resource.success(it)
                }, {
                    users.value = Resource.error(it)
                })
        compositeDisposable.add(disposable)
    }

    private fun getUsersFromLocalAndRemote(isForced: Boolean) {
        val disposable = getFromDatabase()
                .flatMap {
                    users.value = Resource.success(it)
                    users.value = Resource.loading()
                    return@flatMap getFromNetwork(pageNumber)
                }
                .flatMap {
                    return@flatMap insertIntoDatabase(isForced, it)
                }
                .flatMap {
                    return@flatMap getFromDatabase()
                }
                .subscribe({
                    users.value = Resource.success(it)
                }, {
                    users.value = Resource.error(it)
                })
        compositeDisposable.add(disposable)
    }

    private fun getFromDatabase(): Single<List<User>> {
        return userDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getFromNetwork(pageNumber: Int): Single<RandomUserResponse> {
        return apiService.getUsers(pageNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun insertIntoDatabase(forced: Boolean, randomUserResponse: RandomUserResponse?): Single<List<User>> {
        return Single.create<List<User>> {
            val userList = ArrayList<User>()
            if (randomUserResponse?.results != null && randomUserResponse.results!!.isNotEmpty()) {
                for (resultItem in randomUserResponse.results!!) {
                    userList.add(User(0, resultItem.name, resultItem.picture, resultItem.location))
                }
                userDao.insertAllUsers(userList)
            }
            it.onSuccess(userList)
        }
                .doOnSubscribe {
                    // If Forced, then first clear all the data
                    if (forced) {
                        userDao.deleteAll()
                    }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
    }
}