package com.chrisy.bookmark.data

import android.content.Context
import androidx.room.*
import androidx.room.RoomDatabase
import com.chrisy.bookmark.utils.Converters

@Database(entities = [Book::class], version = 1)
@TypeConverters(Converters::class)
abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao

    companion object {
        @Volatile private var INSTANCE: BookDatabase? = null

        fun getDatabase(context: Context): BookDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    BookDatabase::class.java,
                    "book_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
