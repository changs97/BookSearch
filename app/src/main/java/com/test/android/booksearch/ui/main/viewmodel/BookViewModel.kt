package com.test.android.booksearch.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.android.booksearch.data.Book
import com.test.android.booksearch.data.BookApiModel
import com.test.android.booksearch.util.Event
import com.test.android.booksearch.data.source.BookRepository
import com.test.android.booksearch.ui.Action
import com.test.android.booksearch.ui.Intent
import com.test.android.booksearch.ui.State
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import com.test.android.booksearch.ui.reduce
import timber.log.Timber

class BookViewModel : ViewModel() {
    private val repository: BookRepository = BookRepository()

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    fun dispatchIntent(intent: Intent) {
        handleAction(intentToAction(intent))
    }

    private fun intentToAction(intent: Intent): Action {
        return when (intent) {
            is Intent.ClearSearch -> Action.ClearBooks
            is Intent.SearchBooks -> Action.SearchBooks(intent.name)
        }
    }

    private fun handleAction(action: Action) {
        viewModelScope.launch(handler) {
            when (action) {
                is Action.ClearBooks -> {
                    _state.postValue(State.Initial)
                }

                is Action.SearchBooks -> {
                    repository.getBooks(action.name).collect {
                        _state.postValue(it.reduce())
                    }
                }
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, e ->
        Timber.e(e.message)
    }
}