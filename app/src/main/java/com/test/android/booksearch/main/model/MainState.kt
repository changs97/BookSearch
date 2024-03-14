package com.test.android.booksearch.main.model

import com.test.android.booksearch.data.Book


data class MainState(
    val books: List<Book> = emptyList(), val loading: Boolean = false, val error: String? = null
)