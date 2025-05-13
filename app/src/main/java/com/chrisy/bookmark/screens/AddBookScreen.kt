package com.chrisy.bookmark.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.chrisy.bookmark.data.Book
import com.chrisy.bookmark.data.BookStatus
import com.chrisy.bookmark.viewmodel.BookViewModel

@Composable
fun AddBookScreen(
    navController: NavController,
    viewModel: BookViewModel
) {
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    val books by viewModel.books.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Add a New Book", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Book Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = author,
            onValueChange = { author = it },
            label = { Text("Author") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = notes,
            onValueChange = { notes = it },
            label = { Text("Comment") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (title.isNotBlank() && author.isNotBlank()) {
                val newBook = Book(
                    title = title,
                    author = author,
                    status = BookStatus.TO_READ,
                    notes = notes
                )
                viewModel.addBook(newBook)
                navController.popBackStack()
            }
        }) {
            Text("Save Book")
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text("Saved Books:", style = MaterialTheme.typography.titleMedium)
        LazyColumn {
            items(books) { book ->
                var updatedNote by remember(book.id) { mutableStateOf(book.notes) }

                Card(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text("Title: ${book.title}")
                        Text("Author: ${book.author}")
                        Text("Status: ${book.status}")

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = updatedNote,
                            onValueChange = { updatedNote = it },
                            label = { Text("Edit Comment") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Button(onClick = {
                            if (updatedNote != book.notes) {
                                viewModel.updateBook(book.copy(notes = updatedNote))
                            }
                        }) {
                            Text("Save Comment")
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(onClick = {
                                viewModel.deleteBook(book)
                            }) {
                                Text("Delete")
                            }
                            Button(onClick = {
                                val updated = book.copy(status = BookStatus.READ)
                                viewModel.updateBook(updated)
                            }) {
                                Text("Mark as Read")
                            }
                        }
                    }
                }
            }
        }
    }
}