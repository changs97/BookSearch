package com.test.android.booksearch.ui


sealed class Intent {
    data class SearchBooks(val name: String) : Intent()
    object ClearSearch : Intent()
}