package com.test.android.booksearch.data.source.remote

import com.test.android.booksearch.data.BookApiModel
import retrofit2.http.GET
import retrofit2.http.Query

interface BookService {
    @GET("v1/search/book.json")
    suspend fun getBooks(
        @Query("query") query: String, @Query("display") display: Int = 100
    ): BookApiModel
}
