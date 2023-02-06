package com.test.android.moviesearch.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.android.moviesearch.data.Movie
import com.test.android.moviesearch.util.Event
import com.test.android.moviesearch.data.source.MovieRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class MovieViewModel : ViewModel() {
    private val repository: MovieRepository = MovieRepository()

    val query = MutableLiveData<String>()

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = _movies

    private val _openMovieEvent = MutableLiveData<Event<String>>()
    val openMovieEvent: LiveData<Event<String>>
        get() = _openMovieEvent

    fun openMovieLink(url: String) {
        _openMovieEvent.value = Event(url)
    }

    fun search() {
        viewModelScope.launch {
            val query = query.value ?: return@launch
            try {
                val response = repository.getMovies(query)
                _movies.value = response.items
            } catch (e: Exception) {
                Timber.e(e.message)
            }
        }
    }
}