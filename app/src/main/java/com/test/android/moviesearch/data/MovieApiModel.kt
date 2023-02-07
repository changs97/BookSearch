package com.test.android.moviesearch.data

// data class 사용 이유 복습
data class MovieApiModel(
    val display: Int,
    val items: List<Movie>,
    val lastBuildDate: String,
    val start: Int,
    val total: Int
)