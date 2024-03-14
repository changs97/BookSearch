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
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val repository: BookRepository
) : ViewModel(), ContainerHost<MainState, String> {

    override val container: Container<MainState, String> = container(MainState())

    private val handler = CoroutineExceptionHandler { _, e ->
        Timber.e(e.message)
    }


    fun searchBooks(query: String) = intent {
        viewModelScope.launch(handler) {
            runCatching {
                reduce { state.copy(loading = true) }
                repository.getBooks(query).items
            }.onSuccess { books ->
                reduce { state.copy(books = books, loading = false) }
                postSideEffect("${books.size} book(s) loaded")
            }.onFailure {
                reduce { state.copy(error = "Error", loading = false) }
                postSideEffect("Error")
            }
        }
    }
}