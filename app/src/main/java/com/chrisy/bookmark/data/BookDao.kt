package com.chrisy.bookmark.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(book: Book)

    @Update
    suspend fun update(book: Book)

    @Delete
    suspend fun delete(book: Book)

    @Query("SELECT * FROM books WHERE status = :status")
    fun getBooksByStatus(status: BookStatus): Flow<List<Book>>

    @Query("SELECT * FROM books")
    fun getAllBooks(): Flow<List<Book>>

    @Query("SELECT * FROM books WHERE id = :id")
    fun getBookById(id: Int): Flow<Book?>


}