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

    // MutableLiveData, LiveData 존재 이유와 내부 로직 공부
    val query = MutableLiveData<String>()

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = _movies // custom getter/setter 공부

    private val _openMovieEvent = MutableLiveData<Event<String>>()
    val openMovieEvent: LiveData<Event<String>>
        get() = _openMovieEvent

    fun openMovieLink(url: String) {
        _openMovieEvent.value = Event(url)
    }

    fun search() {
        // 코루틴 스코프, 코루틴 에러 처리 공부
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