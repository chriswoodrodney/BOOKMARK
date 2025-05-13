package com.chrisy.bookmark.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val author: String,
    val status: BookStatus,
    val rating: Int = 0,
    val notes: String = ""
)

enum class BookStatus { READING, READ,TO_READ }
