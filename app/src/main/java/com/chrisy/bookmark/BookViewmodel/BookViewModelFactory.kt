package com.chrisy.bookmark.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chrisy.bookmark.data.BookDao

class BookViewModelFactory(private val dao: BookDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BookViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
