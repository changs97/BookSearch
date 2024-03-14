package com.test.android.booksearch.data

import com.google.gson.annotations.SerializedName

data class BookApiModel(
    @SerializedName("display") val display: Int,
    @SerializedName("items") val items: List<Book>,
    @SerializedName("lastBuildDate") val lastBuildDate: String,
    @SerializedName("start") val start: Int,
    @SerializedName("total") val total: Int
)