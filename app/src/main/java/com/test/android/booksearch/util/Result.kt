package com.test.android.booksearch.util

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: CallErrors) : Result<Nothing>()

    object Initial : Result<Nothing>()
    object Loading : Result<Nothing>()
}