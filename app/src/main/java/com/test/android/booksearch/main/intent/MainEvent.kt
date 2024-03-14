package com.test.android.booksearch.main.intent

import com.test.android.booksearch.data.Book

sealed interface MainEvent {
    object Loading : MainEvent
    class Loaded(val books: List<Book>) : MainEvent
}
