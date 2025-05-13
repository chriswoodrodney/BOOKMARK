package com.chrisy.bookmark.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavBackStackEntry
import com.chrisy.bookmark.viewmodel.BookViewModel
import kotlinx.coroutines.launch

@Composable
fun BookDetailScreen(
    bookId: Int,
    viewModel: BookViewModel,
    navController: NavController
) {
    val scope = rememberCoroutineScope()
    val book by viewModel.getBookById(bookId).collectAsState(initial = null)

    book?.let {
        var title by remember { mutableStateOf(it.title) }
        var author by remember { mutableStateOf(it.author) }
        var notes by remember { mutableStateOf(it.notes) }

        Column(modifier = Modifier.padding(16.dp)) {
            Text("Edit Book Details", style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
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
                label = { Text("Notes / Review") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                scope.launch {
                    viewModel.updateBook(
                        it.copy(title = title, author = author, notes = notes)
                    )
                    navController.popBackStack()
                }
            }) {
                Text("Save Changes")
            }
        }
    } ?: Text("Loading book...")
}
