package com.test.android.booksearch.ui


sealed class Action {
    data class SearchBooks(val name: String) : Action()
    object ClearBooks : Action()
}