package com.chrisy.bookmark

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chrisy.bookmark.data.Book
import com.chrisy.bookmark.data.BookDao
import com.chrisy.bookmark.data.BookDatabase
import com.chrisy.bookmark.data.BookStatus
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BookDaoInstrumentedTest {

    private lateinit var db: BookDatabase
    private lateinit var dao: BookDao

    @Before
    fun init() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, BookDatabase::class.java).build()
        dao = db.bookDao()
    }

    @After
    fun cleanup() {
        db.close()
    }

    @Test
    fun insertAndRetrieveBook() = runBlocking {
        val book = Book(title = "Instrumented", author = "Test", status = BookStatus.READING)
        dao.insert(book)
        val result = dao.getBooksByStatus(BookStatus.READING).first()
        assertEquals(1, result.size)
    }
}