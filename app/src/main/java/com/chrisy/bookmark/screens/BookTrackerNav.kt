package com.chrisy.bookmark.screens

import SearchViewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.chrisy.bookmark.data.BookDatabase
import com.chrisy.bookmark.repository.BookRepository
import com.chrisy.bookmark.viewmodel.BookViewModel
import com.chrisy.bookmark.viewmodel.BookViewModelFactory
import com.chrisy.bookmark.viewmodel.SearchViewModelFactory
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookTrackerNav() {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Book Tracker") },
                actions = {
                    TextButton(onClick = { navController.navigate("dashboard") }) {
                        Text("Dashboard")
                    }
                }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "bookList",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("bookList") {
                val context = LocalContext.current
                val dao = BookDatabase.getDatabase(context).bookDao()
                val factory = BookViewModelFactory(dao)
                val bookViewModel: BookViewModel = viewModel(factory = factory)
                BookListScreen(navController = navController, viewModel = bookViewModel)
            }

            composable("addBook") {
                val context = LocalContext.current
                val dao = BookDatabase.getDatabase(context).bookDao()
                val factory = BookViewModelFactory(dao)
                val bookViewModel: BookViewModel = viewModel(factory = factory)
                AddBookScreen(navController = navController, viewModel = bookViewModel)
            }

            composable(
                "search/{apiKey}",
                arguments = listOf(navArgument("apiKey") { type = NavType.StringType })
            ) { backStackEntry ->
                val context = LocalContext.current
                val apiKey = backStackEntry.arguments?.getString("apiKey") ?: ""
                val dao = BookDatabase.getDatabase(context).bookDao()
                val factory = SearchViewModelFactory(BookRepository(dao))
                val searchViewModel: SearchViewModel = viewModel(factory = factory)
                SearchBookScreen(apiKey = apiKey, viewModel = searchViewModel)
            }

            composable(
                "bookDetail/{bookId}",
                arguments = listOf(navArgument("bookId") { type = NavType.IntType })
            ) { backStackEntry ->
                val context = LocalContext.current
                val dao = BookDatabase.getDatabase(context).bookDao()
                val factory = BookViewModelFactory(dao)
                val bookViewModel: BookViewModel = viewModel(factory = factory)
                val bookId = backStackEntry.arguments?.getInt("bookId") ?: 0
                BookDetailScreen(bookId = bookId, viewModel = bookViewModel, navController = navController)
            }

            composable("dashboard") {
                val context = LocalContext.current
                val dao = BookDatabase.getDatabase(context).bookDao()
                val factory = BookViewModelFactory(dao)
                val bookViewModel: BookViewModel = viewModel(factory = factory)
                val allBooksState = bookViewModel.allBooks.collectAsState(initial = emptyList())
                val allBooks = allBooksState.value

                Column(modifier = Modifier.padding(16.dp)) {
                    Text("\uD83D\uDCCA Statistics Dashboard", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(16.dp))

                    val total = allBooks.size
                    val read = allBooks.count { it.status == com.chrisy.bookmark.data.BookStatus.READ }
                    val reading = allBooks.count { it.status == com.chrisy.bookmark.data.BookStatus.READING }
                    val commented = allBooks.count { it.notes.isNotBlank() }
                    val avgRating = allBooks.map { it.rating }.filter { it > 0 }.average()

                    Text("Total Books: $total")
                    Text("Books Read: $read")
                    Text("Currently Reading: $reading")
                    Text("Books with Comments: $commented")
                    Text("Average Rating: ${if (avgRating.isNaN()) "N/A" else String.format("%.1f", avgRating)}")
                }
            }
        }
    }
}
