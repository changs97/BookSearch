package com.test.android.moviesearch.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import com.test.android.moviesearch.base.BaseActivity
import com.test.android.moviesearch.ui.main.adapter.MovieAdapter
import com.test.android.moviesearch.ui.main.viewmodel.MovieViewModel
import com.test.android.moviesearch.util.eventObserve
import com.test.android.moviesearch.R
import com.test.android.moviesearch.databinding.ActivityMainBinding


class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main), MovieItemNavigator {
    private val viewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.recyclerView.adapter = MovieAdapter(viewModel)

        viewModel.openMovieEvent.eventObserve(this) { url ->
            openMovieLink(url)
        }
    }

    override fun openMovieLink(url: String) {
        val intent =
            Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}