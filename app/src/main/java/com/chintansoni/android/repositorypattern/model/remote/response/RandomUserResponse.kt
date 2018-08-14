package com.chintansoni.android.repositorypattern.model.remote.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class RandomUserResponse(@SerializedName("results")
                              var results: List<ResultsItem>?)

data class ResultsItem(@SerializedName("phone")
                       var phone: String = "",
                       @SerializedName("dob")
                       var dob: Dob,
                       @SerializedName("name")
                       var name: Name,
                       @SerializedName("location")
                       var location: Location,
                       @SerializedName("cell")
                       var cell: String = "",
                       @SerializedName("email")
                       var email: String = "",
                       @SerializedName("picture")
                       var picture: Picture)

@Parcelize
data class Dob(@SerializedName("date")
               var date: String = "",
               @SerializedName("age")
               var age: Int = 0) : Parcelable

@Parcelize
data class Picture(@SerializedName("thumbnail")
                   var thumbnail: String = "",
                   @SerializedName("large")
                   var large: String = "",
                   @SerializedName("medium")
                   var medium: String = "") : Parcelable

@Parcelize
data class Name(@SerializedName("last")
                var last: String = "",
                @SerializedName("first")
                var first: String = "") : Parcelable

@Parcelize
data class Location(@SerializedName("city")
                    var city: String = "",
                    @SerializedName("state")
                    var state: String = "") : Parcelable


