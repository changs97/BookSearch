package com.test.android.booksearch.ui

import com.test.android.booksearch.data.Book
import com.test.android.booksearch.data.BookApiModel
import com.test.android.booksearch.util.Result


fun Result<List<Book>>.reduce(): State {
    return when (this) {
        is Result.Initial -> State.Initial
        is Result.Success -> State.ResultSearch(data)
        is Result.Error -> State.Exception(exception)
        is Result.Loading -> State.Loading
    }
}