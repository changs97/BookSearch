package com.test.android.booksearch.ui.main

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.GridLayoutManager
import com.test.android.booksearch.R
import com.test.android.booksearch.base.BaseActivity
import com.test.android.booksearch.databinding.ActivityMainBinding
import com.test.android.booksearch.ui.Intent
import com.test.android.booksearch.ui.State
import com.test.android.booksearch.ui.main.adapter.BookAdapter
import com.test.android.booksearch.ui.main.viewmodel.BookViewModel
import com.test.android.booksearch.util.eventObserve
import com.test.android.booksearch.util.getMessage
import com.test.android.booksearch.util.runIfTrue

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val viewModel: BookViewModel by viewModels()
    private val adapter: BookAdapter by lazy { BookAdapter(viewModel) }

    private lateinit var viewState: State
    private val state get() = viewState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        dispatchIntent(Intent.ClearSearch)

        viewModel.state.observe(this) {
            viewState = it
            render(it)
        }

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
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = layoutManager

        binding.mainImgBtnSearch.setOnClickListener {
            binding.mainEditTxt.text.isNullOrBlank().not().runIfTrue {
                dispatchIntent(Intent.SearchBooks(binding.mainEditTxt.text.toString()))
            }
        }

        binding.mainEditTxt.doOnTextChanged { text, _, _, _ ->
            text.isNullOrBlank()
                .and(state is State.ResultSearch)
                .runIfTrue {
                    dispatchIntent(Intent.ClearSearch)
                }
        }
/*
        viewModel.openBookEvent.eventObserve(this) { url ->
            openBookLink(url)
        }
        */
    }

/*
    private fun openBookLink(url: String) {
        val intent = android.content.Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
*/

    private fun dispatchIntent(intent: Intent) {
        viewModel.dispatchIntent(intent)
    }

    private fun render(state: State) {
        binding.progress.isVisible = state is State.Loading

        when (state) {
            is State.ResultSearch -> {
                adapter.submitList(state.data)
            }

            is State.Exception -> {
                Toast.makeText(this, state.callErrors.getMessage(this), Toast.LENGTH_SHORT).show()
            }

            else -> {}
        }
    }
}