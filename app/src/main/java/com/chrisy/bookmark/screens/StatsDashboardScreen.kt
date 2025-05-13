package com.chrisy.bookmark.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chrisy.bookmark.viewmodel.BookViewModel

@Composable
fun StatsDashboardScreen(viewModel: BookViewModel) {
    val books by viewModel.allBooks.collectAsState(initial = emptyList())

    val totalBooks = books.size
    val readCount = books.count { it.status.name == "READ" }
    val toReadCount = books.count { it.status.name == "TO_READ" || it.status.name == "WANT_TO_READ" }
    val readingCount = books.count { it.status.name == "READING" }
    val avgRating = books.filter { it.rating > 0 }.map { it.rating }.average().takeIf { !it.isNaN() } ?: 0.0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("📊 Reading Statistics", style = MaterialTheme.typography.headlineSmall)

        Text("Total Books: $totalBooks")
        Text("Read: $readCount")
        Text("Currently Reading: $readingCount")
        Text("To Read: $toReadCount")
        Text("Average Rating: %.1f".format(avgRating))

        val percentRead = if (totalBooks > 0) (readCount * 100 / totalBooks) else 0
        LinearProgressIndicator(
            progress = percentRead / 100f,
            modifier = Modifier.fillMaxWidth()
        )
        Text("Progress: $percentRead% read")
    }
}
