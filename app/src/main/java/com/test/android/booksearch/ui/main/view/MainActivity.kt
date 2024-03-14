package com.test.android.booksearch.ui.main.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.lifecycleScope
import com.test.android.booksearch.ui.main.viewmodel.BookViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {
    private val viewModel: BookViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.sideEffects.onEach {
            Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
        }.launchIn(lifecycleScope)

        setContent {
            MaterialTheme {
                Surface {
                    MainScreen(state = viewModel.state.collectAsState().value,
                        onSearchBooks = { query: String -> viewModel.searchBooks(query = query) })
                }
            }
        }
    }
}