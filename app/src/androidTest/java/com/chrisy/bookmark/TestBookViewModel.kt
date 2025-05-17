package com.chrisy.bookmark

import com.chrisy.bookmark.viewmodel.BookViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.chrisy.bookmark.data.Book
import com.chrisy.bookmark.data.BookStatus

class TestBookViewModel : BookViewModel(
    object : com.chrisy.bookmark.data.BookDao {
        private val books = mutableListOf<Book>()
        private val flow = MutableStateFlow<List<Book>>(emptyList())

        override fun getBooksByStatus(status: BookStatus): StateFlow<List<Book>> {
            return flow
        }

        override suspend fun insert(book: Book) {
            books.add(book)
            flow.value = books
        }

        override suspend fun update(book: Book) {}
        override suspend fun delete(book: Book) {}
        override fun getBookById(id: Int) = MutableStateFlow<Book?>(null)
        override fun getAllBooks() = MutableStateFlow<List<Book>>(books)
    }
)
