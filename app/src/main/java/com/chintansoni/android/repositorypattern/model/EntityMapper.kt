package com.chintansoni.android.repositorypattern.model

import com.chintansoni.android.repositorypattern.model.local.entity.User
import com.chintansoni.android.repositorypattern.model.remote.response.RandomUserResponse
import io.reactivex.functions.Function

object EntityMapper {
    fun convert(): Function<RandomUserResponse, List<User>> {
        return Function {
            val users: ArrayList<User> = ArrayList()
            for (result in it.results!!.iterator()) {
                users.add(User(id = 0,
                        name = result.name,
                        email = result.email,
                        gender = result.gender,
                        cell = result.cell,
                        picture = result.picture,
                        location = result.location,
                        dob = result.dob))
            }
            users.toList()
        }
    }
}