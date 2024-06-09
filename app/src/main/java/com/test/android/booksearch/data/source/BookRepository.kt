package com.test.android.booksearch.data.source

import com.test.android.booksearch.data.source.remote.ApiHelper
import javax.inject.Inject


class BookRepository @Inject constructor(
    private val apiHelper: ApiHelper
) {
    suspend fun getBooks(query: String) = apiHelper.getBooks(query)
}
