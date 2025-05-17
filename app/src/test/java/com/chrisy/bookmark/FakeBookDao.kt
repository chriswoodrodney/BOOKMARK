package com.chrisy.bookmark

import com.chrisy.bookmark.data.Book
import com.chrisy.bookmark.data.BookDao
import com.chrisy.bookmark.data.BookStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeBookDao : BookDao {
    private val booksFlow = MutableStateFlow<List<Book>>(emptyList())

    override fun getBooksByStatus(status: BookStatus): Flow<List<Book>> {
        return booksFlow
    }

    override fun getAllBooks(): Flow<List<Book>> {
        TODO("Not yet implemented")
    }

    override suspend fun insert(book: Book) {
        booksFlow.value = booksFlow.value + book
    }

    override suspend fun update(book: Book) {
        booksFlow.value = booksFlow.value.map { if (it.id == book.id) book else it }
    }

    override suspend fun delete(book: Book) {
        booksFlow.value = booksFlow.value.filterNot { it.id == book.id }
    }

    override fun getBookById(id: Int): Flow<Book?> {
        return MutableStateFlow(booksFlow.value.find { it.id == id })
    }
}