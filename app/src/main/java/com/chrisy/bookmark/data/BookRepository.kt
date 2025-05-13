package com.chrisy.bookmark.repository

import com.chrisy.bookmark.data.Book
import com.chrisy.bookmark.data.BookDao
import com.chrisy.bookmark.data.BookStatus
import com.chrisy.bookmark.network.RetrofitInstance
import kotlinx.coroutines.flow.Flow

class BookRepository(private val dao: BookDao) {

    fun getBooksByStatus(status: BookStatus): Flow<List<Book>> = dao.getBooksByStatus(status)

    suspend fun addBook(book: Book) = dao.insert(book)

    suspend fun updateBook(book: Book) = dao.update(book)

    suspend fun deleteBook(book: Book) = dao.delete(book)

    suspend fun searchBooks(query: String, apiKey: String) =
        RetrofitInstance.api.searchBooks(query, apiKey)

}