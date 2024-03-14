package com.test.android.booksearch.data.source

import com.test.android.booksearch.data.source.remote.BookService
import javax.inject.Inject


class BookRepository @Inject constructor(
    private val service: BookService
) {
    suspend fun getBooks(query: String) = service.getBooks(query)
}
