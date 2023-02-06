package com.test.android.moviesearch.data

data class MovieApiModel(
    val display: Int,
    val items: List<Movie>,
    val lastBuildDate: String,
    val start: Int,
    val total: Int
)