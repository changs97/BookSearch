package com.test.android.booksearch.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.test.android.booksearch.R
import com.test.android.booksearch.base.BaseActivity
import com.test.android.booksearch.databinding.ActivityMainBinding
import com.test.android.booksearch.ui.main.adapter.BookAdapter
import com.test.android.booksearch.ui.main.viewmodel.BookViewModel
import com.test.android.booksearch.util.eventObserve
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val viewModel: BookViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

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
        binding.recyclerView.adapter = BookAdapter(viewModel)
        binding.recyclerView.layoutManager = layoutManager

        viewModel.openBookEvent.eventObserve(this) { url ->
            openBookLink(url)
        }
    }

    private fun openBookLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}