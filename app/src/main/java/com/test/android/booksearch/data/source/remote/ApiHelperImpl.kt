package com.test.android.booksearch.data.source.remote

import com.test.android.booksearch.data.BookApiModel
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val service: BookService
) : ApiHelper {
    override suspend fun getBooks(query: String): BookApiModel {
        return service.getBooks(query)
    }
}