package com.test.android.booksearch.main.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.lifecycleScope
import com.test.android.booksearch.main.viewmodel.BookViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import org.orbitmvi.orbit.viewmodel.observe

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: BookViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.container.sideEffectFlow.onEach {
            Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
        }.launchIn(lifecycleScope)

        setContent {
            MaterialTheme {
                Surface {
                    MainScreen(state = viewModel.container.stateFlow.collectAsState().value,
                        onSearchBooks = { query: String -> viewModel.searchBooks(query = query) },
                        onClickBook = { link: String ->
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                            startActivity(intent)
                        })
                }
            }
        }
    }
}