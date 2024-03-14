package com.test.android.booksearch.data.source.remote

import com.test.android.booksearch.data.BookApiModel

interface ApiHelper {
    suspend fun getBooks(query: String): BookApiModel
}