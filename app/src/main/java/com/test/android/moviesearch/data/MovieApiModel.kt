package com.test.android.moviesearch.data

import com.google.gson.annotations.SerializedName

// data class 사용 이유 복습
data class MovieApiModel(
    @SerializedName("display")
    val display: Int,
    @SerializedName("items")
    val items: List<Movie>,
    @SerializedName("lastBuildDate")
    val lastBuildDate: String,
    @SerializedName("start")
    val start: Int,
    @SerializedName("total")
    val total: Int
)