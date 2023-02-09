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

// Base.. 사용 이유 공부
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    // 이렇게 사용할 수 있는 이유와 이렇게 사용하는 이유 공부, 팩토리 패턴, 뷰홀더 패턴 등 자주 사용하는 디자인 패턴 복습
    private val viewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) { // Bundle 공부
        super.onCreate(savedInstanceState) // savedInstanceState 공부
        binding.lifecycleOwner = this // 생명주기, viewLifecycleOwner, context 공부 (뷰에서 context 접근?)
        binding.viewModel = viewModel // ViewModel 공부
        binding.recyclerView.adapter = MovieAdapter(viewModel)

        viewModel.openMovieEvent.eventObserve(this) { url ->
            openMovieLink(url)
        }
    }

    // Navigator 인터페이스 사용 이유 공부, 이렇게 할 이유? (인터페이스를 사용하는 이유를 공부해보자.)
    private fun openMovieLink(url: String) {
        val intent =
            Intent(Intent.ACTION_VIEW, Uri.parse(url)) // 인텐트 공부
        startActivity(intent)
    }
}