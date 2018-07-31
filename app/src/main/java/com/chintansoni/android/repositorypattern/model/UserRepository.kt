package com.chintansoni.android.repositorypattern.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.chintansoni.android.repositorypattern.model.local.dao.UserDao
import com.chintansoni.android.repositorypattern.model.local.entity.User
import com.chintansoni.android.repositorypattern.model.remote.ApiService
import com.chintansoni.android.repositorypattern.model.remote.response.ResultsItem
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private var apiService: ApiService, private var userDao: UserDao) {

    private val users = MutableLiveData<Resource<List<User>>>()
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun getUsers(page: Int, isForced: Boolean): LiveData<Resource<List<User>>> {
        getLocalUsers()
        getRemoteUsers(isForced, page)
        return users
    }

    internal fun getRemoteUsers(isForced: Boolean, page: Int) {
        users.value = Resource.loading()
        val disposable = apiService.getUsers(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    insertIntoLocal(isForced, it.results!!)
                }, {
                    users.value = Resource.error(Exception(it))
                })
        compositeDisposable.add(disposable)
    }

    private fun insertIntoLocal(isForced: Boolean, results: List<ResultsItem>) {
        val disposable = Observable.fromIterable(results)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    Timber.tag("UserRepository").d("DoOnSubscribe")

                    // If Forced, then first clear all the data
                    if (isForced) {
                        userDao.deleteAll()
                    }
                }
                .observeOn(Schedulers.io())
                .doOnComplete {
                    Timber.tag("UserRepository").d("DoOnComplete")

                    // again fetch and return users from Local
                    getLocalUsers()
                }
                .subscribe({
                    userDao.insert(User(0, it.name, it.picture, it.location))
                }, {

                })
        compositeDisposable.add(disposable)
    }

    private fun getLocalUsers() {
        val disposable = userDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    users.value = Resource.success(it)
                }, {
                    users.value = Resource.error(Exception(it))
                })
        compositeDisposable.add(disposable)
    }
}