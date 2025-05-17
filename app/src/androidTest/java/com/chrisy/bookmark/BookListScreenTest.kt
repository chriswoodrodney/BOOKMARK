package com.chrisy.bookmark

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.chrisy.bookmark.data.Book
import com.chrisy.bookmark.data.BookStatus
import com.chrisy.bookmark.screens.BookListScreen
import com.chrisy.bookmark.viewmodel.BookViewModel
import org.junit.Rule
import org.junit.Test

class BookListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun screenShowsBookTitle() {
        val fakeViewModel = TestBookViewModel()
        composeTestRule.setContent {
            BookListScreen(navController = androidx.navigation.compose.rememberNavController(), viewModel = fakeViewModel)
        }
        composeTestRule.onNodeWithText("Your Book List").assertIsDisplayed()
    }

    // A mock ViewModel can be defined or injected with Compose previews/testing
}