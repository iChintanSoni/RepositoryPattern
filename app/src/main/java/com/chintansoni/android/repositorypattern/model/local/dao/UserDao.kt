package com.chintansoni.android.repositorypattern.model.local.dao

import androidx.room.*
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
    fun insert(userList: List<User>)

    @Query("DELETE FROM " + DatabaseConstants.mTableUser)
    fun deleteAll()

    @Transaction
    fun insertAllUsers(userList: List<User>) {
        insert(userList)
    }
}