package com.chintansoni.android.repositorypattern.model.local.entity

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import com.chintansoni.android.repositorypattern.model.local.DatabaseConstants
import com.chintansoni.android.repositorypattern.model.remote.response.Dob
import com.chintansoni.android.repositorypattern.model.remote.response.Location
import com.chintansoni.android.repositorypattern.model.remote.response.Name
import com.chintansoni.android.repositorypattern.model.remote.response.Picture
import kotlinx.android.parcel.Parcelize


@Entity(tableName = DatabaseConstants.mTableUser)
@Parcelize
data class User(
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        @Embedded
        val name: Name,
        val email: String,
        val cell: String,
        @Embedded
        val picture: Picture,
        @Embedded
        val location: Location,
        @Embedded
        val dob: Dob) : Parcelable