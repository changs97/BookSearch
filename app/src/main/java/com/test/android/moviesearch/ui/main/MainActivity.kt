package com.test.android.moviesearch.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.test.android.moviesearch.base.BaseActivity
import com.test.android.moviesearch.ui.main.adapter.MovieAdapter
import com.test.android.moviesearch.ui.main.viewmodel.MovieViewModel
import com.test.android.moviesearch.util.eventObserve
import com.test.android.moviesearch.R
import com.test.android.moviesearch.databinding.ActivityMainBinding


class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val viewModel: MovieViewModel by viewModels() // 위임 패턴 공부

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel // ViewModel 공부

        // 코드 개선하기
        val layoutManager = GridLayoutManager(this, 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val i = (position + 1) % 5
                return if (i == 0) {
                    2
                } else {
                    1
                }
            }
        }
        binding.recyclerView.adapter = MovieAdapter(viewModel)
        binding.recyclerView.layoutManager = layoutManager

        viewModel.openMovieEvent.eventObserve(this) { url ->
            openMovieLink(url)
        }
    }

    private fun openMovieLink(url: String) {
        val intent =
            Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}