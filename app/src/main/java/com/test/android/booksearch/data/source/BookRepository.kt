package com.test.android.booksearch.data.source

import com.test.android.booksearch.data.source.remote.BookService

class BookRepository {
    private val service: BookService = BookService.create()

    suspend fun getBooks(query: String) = service.getBooks(query)
}
