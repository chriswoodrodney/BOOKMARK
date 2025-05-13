package com.chrisy.bookmark.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chrisy.bookmark.data.Book
import com.chrisy.bookmark.data.BookDao
import com.chrisy.bookmark.data.BookStatus
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class BookViewModel(private val dao: BookDao) : ViewModel() {

    private val _status = MutableStateFlow(BookStatus.TO_READ)
    val status: StateFlow<BookStatus> = _status

    val books: StateFlow<List<Book>> = _status
        .flatMapLatest { dao.getBooksByStatus(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allBooks: StateFlow<List<Book>> = dao.getAllBooks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun setStatus(newStatus: BookStatus) {
        _status.value = newStatus
    }

    fun clearStatusFilter() {
        _status.value = BookStatus.TO_READ // or use null if you've made status nullable
    }

    fun addBook(book: Book) = viewModelScope.launch {
        dao.insert(book)
    }

    fun updateBook(book: Book) = viewModelScope.launch {
        dao.update(book)
    }

    fun deleteBook(book: Book) = viewModelScope.launch {
        dao.delete(book)
    }

    fun getBookById(id: Int): Flow<Book?> {
        return dao.getBookById(id)
    }
}
