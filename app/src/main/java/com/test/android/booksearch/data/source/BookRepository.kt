package com.test.android.booksearch.data.source

import com.test.android.booksearch.data.source.remote.BookService
import com.test.android.booksearch.util.CallErrors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import com.test.android.booksearch.util.Result
import com.test.android.booksearch.util.applyCommonSideEffects

class BookRepository {
    private val service: BookService = BookService.create()


    fun getBooks(query: String) = flow {
        service.getBooks(query).run {
            if (isSuccessful) {
                if (this.body() == null) {
                    emit(Result.Error(CallErrors.ErrorEmptyData))
                } else {
                    emit(Result.Success(this.body()!!.items))
                }
            } else {
                emit(Result.Error(CallErrors.ErrorServer))
            }
        }
    }.applyCommonSideEffects()
}


