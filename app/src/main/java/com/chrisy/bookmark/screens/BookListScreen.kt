package com.chrisy.bookmark.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.chrisy.bookmark.BuildConfig
import com.chrisy.bookmark.data.BookStatus
import com.chrisy.bookmark.viewmodel.BookViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreen(navController: NavController, viewModel: BookViewModel) {
    val books by viewModel.books.collectAsState()
    val currentStatus by viewModel.status.collectAsState()
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Your Book List", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(8.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = currentStatus.name,
                onValueChange = {},
                readOnly = true,
                label = { Text("Filter by Status") },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                BookStatus.values().forEach { status ->
                    DropdownMenuItem(
                        text = { Text(status.name) },
                        onClick = {
                            viewModel.setStatus(status)
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { navController.navigate("addBook") }) {
            Text("Add Book")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            val apiKey = BuildConfig.BOOKS_API_KEY
            println("[BookListScreen] Navigating to search with apiKey: $apiKey")
            navController.navigate("search/$apiKey")
        }) {
            Text("Search Online")
        }


        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(books) { book ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable {
                            navController.navigate("bookDetail/${book.id}")
                        }
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text("Title: ${book.title}")
                        Text("Author: ${book.author}")
                        Text("Status: ${book.status}")
                        if (book.notes.isNotBlank()) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "Comment: ${book.notes}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}

