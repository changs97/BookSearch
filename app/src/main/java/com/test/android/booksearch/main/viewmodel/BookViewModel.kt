package com.test.android.booksearch.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.android.booksearch.data.source.BookRepository
import com.test.android.booksearch.main.intent.MainEvent
import com.test.android.booksearch.main.model.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BookViewModel@Inject constructor(
    private val repository: BookRepository
) : ViewModel() {

    private val handler = CoroutineExceptionHandler { _, e ->
        Timber.e(e.message)
    }

    private val events = Channel<MainEvent>()

    val state: StateFlow<MainState> = events.receiveAsFlow().runningFold(MainState(), ::reduceState)
        .stateIn(viewModelScope, SharingStarted.Eagerly, MainState())

    private val _sideEffects = Channel<String>()

    val sideEffects = _sideEffects.receiveAsFlow()

    private fun reduceState(current: MainState, event: MainEvent): MainState {
        return when (event) {
            is MainEvent.Loading -> {
                current.copy(loading = true)
            }

            is MainEvent.Loaded -> {
                current.copy(loading = false, books = event.books)
            }
        }
    }

    fun searchBooks(query: String) {
        viewModelScope.launch(handler) {
            events.send(MainEvent.Loading)
            val books = repository.getBooks(query).items
            events.send(MainEvent.Loaded(books))
            _sideEffects.send("${books.size} book(s) loaded")
        }
    }
}