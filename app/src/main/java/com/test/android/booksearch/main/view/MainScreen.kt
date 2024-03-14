package com.test.android.booksearch.main.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.test.android.booksearch.data.Book
import com.test.android.booksearch.main.model.MainState

@Composable
fun MainScreen(
    state: MainState, onSearchBooks: (String) -> Unit
) {
    Column {
        val queryState = remember { mutableStateOf(TextFieldValue()) }
        SearchBar(queryState = queryState, onSearch = { query ->
            if (query.isNotEmpty()) {
                onSearchBooks(query)
            }
        })

        if (state.books.isEmpty() || state.error != null) {
            if (!state.loading) {
                EmptyScreen()
            }
        } else {
            BookList(state.books)
        }
        if (state.loading) {
            LoadingProgressBar()
        }
    }


}

@Composable
fun SearchBar(queryState: MutableState<TextFieldValue>, onSearch: (String) -> Unit) {
    OutlinedTextField(value = queryState.value,
        onValueChange = { queryState.value = it },
        label = { Text("책 제목") },
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = {
            IconButton(onClick = { /* TODO */ }) {
                Icon(Icons.Default.Search, contentDescription = "Search")
            }
        },
        trailingIcon = {
            IconButton(onClick = {
                onSearch(queryState.value.text)
            }) {
                Icon(Icons.Default.ArrowForward, contentDescription = "Search")
            }
        })
}

@Composable
fun BookList(books: List<Book>) {
    LazyColumn {
        items(books) { book ->
            BookItem(book = book)
            Divider()
        }
    }
}

@Composable
fun BookItem(book: Book) {
    Row {
        AsyncImage(
            model = book.image,
            contentDescription = "book image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.width(5.dp))

        Column {
            Text(text = book.title, maxLines = 1, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = book.description, maxLines = 5, fontSize = 10.sp)
        }
    }
}

@Composable
fun EmptyScreen() {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Text(text = "Empty")
    }
}

@Composable
fun LoadingProgressBar() {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
