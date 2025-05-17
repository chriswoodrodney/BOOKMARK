package com.chrisy.bookmark

import app.cash.turbine.test
import com.chrisy.bookmark.data.Book
import com.chrisy.bookmark.data.BookStatus
import com.chrisy.bookmark.viewmodel.BookViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class BookViewModelTest {

    private lateinit var viewModel: BookViewModel
    private lateinit var fakeDao: FakeBookDao

    @Before
    fun setup() {
        fakeDao = FakeBookDao()
        viewModel = BookViewModel(fakeDao)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun addBook_shouldEmitNewBook(): Unit = runTest {
        val book = Book(title = "Test Book", author = "Author", status = BookStatus.TO_READ)
        viewModel.addBook(book)
        val books = viewModel.books.first()
        assertTrue(books.any { it.title == "Test Book" })
    }

}