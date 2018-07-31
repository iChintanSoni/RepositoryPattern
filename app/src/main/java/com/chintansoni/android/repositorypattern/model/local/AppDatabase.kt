package com.chintansoni.android.repositorypattern.model.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.chintansoni.android.repositorypattern.model.local.dao.UserDao
import com.chintansoni.android.repositorypattern.model.local.entity.User

@Database(entities = [User::class], version = DatabaseConstants.mVersion)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}