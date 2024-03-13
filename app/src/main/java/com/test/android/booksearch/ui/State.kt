package com.test.android.booksearch.ui

import com.test.android.booksearch.util.CallErrors
import com.test.android.booksearch.data.Book


sealed class State {
    object Loading : State()
    object Initial : State()
    data class ResultSearch(val data : List<Book>): State()
    data class Exception(val callErrors: CallErrors) : State()
}