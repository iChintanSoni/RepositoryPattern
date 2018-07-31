package com.chintansoni.android.repositorypattern.model.local.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.chintansoni.android.repositorypattern.model.local.DatabaseConstants
import com.chintansoni.android.repositorypattern.model.local.entity.User
import io.reactivex.Single

@Dao
interface UserDao {
    @Query("SELECT * FROM " + DatabaseConstants.mTableUser)
    fun getAll(): Single<List<User>>

    @Query("SELECT * FROM " + DatabaseConstants.mTableUser)
    fun getAllSync(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Query("DELETE FROM " + DatabaseConstants.mTableUser)
    fun deleteAll()
}