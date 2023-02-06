package com.test.android.moviesearch.ui.main.adapter

import com.test.android.moviesearch.data.Movie


interface MovieItemUserActionsListener {
    fun onItemClicked(item: Movie)
}