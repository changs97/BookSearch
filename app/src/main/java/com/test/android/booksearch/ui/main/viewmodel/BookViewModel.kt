package com.test.android.booksearch.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.android.booksearch.data.Book
import com.test.android.booksearch.util.Event
import com.test.android.booksearch.data.source.BookRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber

class BookViewModel : ViewModel() {
    private val repository: BookRepository = BookRepository()

    val query = MutableLiveData<String>()

    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>>
        get() = _books

    private val _spinner = MutableLiveData<Boolean>(false)

    val spinner: LiveData<Boolean>
        get() = _spinner

    private val _openBookEvent = MutableLiveData<Event<String>>()
    val openBookEvent: LiveData<Event<String>>
        get() = _openBookEvent

    fun openMovieLink(url: String) {
        _openBookEvent.value = Event(url)
    }

    fun search() {
        viewModelScope.launch(handler) {
            _spinner.value = true
            val query = query.value ?: return@launch
            val response = repository.getBooks(query)
            _books.value = response.items
            _spinner.value = false
        }
    }


    private val handler = CoroutineExceptionHandler { _, e ->
        Timber.e(e.message)
    }
}