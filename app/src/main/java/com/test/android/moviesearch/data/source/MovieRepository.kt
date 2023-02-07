package com.test.android.moviesearch.data.source

import com.test.android.moviesearch.data.source.remote.MovieService

class MovieRepository {
    private val service: MovieService = MovieService.create()

    suspend fun getMovies(query: String) = service.getMovies(query)
}
