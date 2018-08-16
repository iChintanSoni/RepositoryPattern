package com.chintansoni.android.repositorypattern.model.remote

import com.chintansoni.android.repositorypattern.model.remote.response.RandomUserResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api")
    fun getUsers(@Query("page") page: Int,
                 @Query("results") result: Int = 10,
                 @Query("lang") lang: String = "en"): Single<RandomUserResponse>
}