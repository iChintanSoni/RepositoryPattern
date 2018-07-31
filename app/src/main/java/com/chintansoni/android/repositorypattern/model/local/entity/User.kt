package com.chintansoni.android.repositorypattern.model.local.entity

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.chintansoni.android.repositorypattern.model.local.DatabaseConstants
import com.chintansoni.android.repositorypattern.model.remote.response.Location
import com.chintansoni.android.repositorypattern.model.remote.response.Name
import com.chintansoni.android.repositorypattern.model.remote.response.Picture

@Entity(tableName = DatabaseConstants.mTableUser)
data class User(
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        @Embedded
        val name: Name,
        @Embedded
        val picture: Picture,
        @Embedded
        val location: Location)