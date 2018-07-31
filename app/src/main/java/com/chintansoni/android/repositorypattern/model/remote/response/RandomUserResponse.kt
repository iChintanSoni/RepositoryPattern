package com.chintansoni.android.repositorypattern.model.remote.response

import com.google.gson.annotations.SerializedName

data class RandomUserResponse(@SerializedName("results")
                              var results: List<ResultsItem>?,
                              @SerializedName("info")
                              var info: Info)


data class ResultsItem(@SerializedName("name")
                       var name: Name,
                       @SerializedName("location")
                       var location: Location,
                       @SerializedName("picture")
                       var picture: Picture)


data class Picture(@SerializedName("thumbnail")
                   var thumbnail: String = "",
                   @SerializedName("large")
                   var large: String = "",
                   @SerializedName("medium")
                   var medium: String = "")


data class Info(@SerializedName("seed")
                var seed: String = "",
                @SerializedName("page")
                val page: Int = 0,
                @SerializedName("results")
                var results: Int = 0,
                @SerializedName("version")
                var version: String = "")


data class Name(@SerializedName("last")
                var last: String = "",
                @SerializedName("title")
                var title: String = "",
                @SerializedName("first")
                var first: String = "")


data class Location(@SerializedName("city")
                    var city: String = "",
                    @SerializedName("street")
                    var street: String = "",
//                    @SerializedName("postcode")
//                    var postcode: Any,
                    @SerializedName("state")
                    var state: String = "")


