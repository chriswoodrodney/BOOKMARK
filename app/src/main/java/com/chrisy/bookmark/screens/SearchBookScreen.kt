package com.chrisy.bookmark.screens

import SearchViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chrisy.bookmark.network.BookItem
import com.chrisy.bookmark.data.Book
import com.chrisy.bookmark.data.BookStatus
import kotlinx.coroutines.launch

@Composable
fun SearchBookScreen(apiKey: String, viewModel: SearchViewModel = viewModel()) {
    var query by remember { mutableStateOf("") }
    val results by viewModel.searchResults.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Search books") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { viewModel.searchBooks(query, apiKey) }) {
            Text("Search")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(results.size) { index ->
                val book: BookItem = results[index]
                val info = book.volumeInfo
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text("Title: ${info.title ?: "Unknown"}")
                        Text("Author(s): ${info.authors?.joinToString() ?: "N/A"}")
                        Text("Description: ${info.description ?: "No description."}")
                        Spacer(modifier = Modifier.height(4.dp))
                        Button(onClick = {
                            coroutineScope.launch {
                                val savedBook = Book(
                                    title = info.title ?: "Untitled",
                                    author = info.authors?.joinToString() ?: "Unknown",
                                    status = BookStatus.TO_READ,
                                    notes = info.description ?: ""
                                )
                                viewModel.addBook(savedBook)
                            }
                        }) {
                            Text("Save to Library")
                        }
                    }
                }
            }
        }
    }
}
