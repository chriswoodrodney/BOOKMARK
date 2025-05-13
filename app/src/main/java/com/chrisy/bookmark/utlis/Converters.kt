package com.chrisy.bookmark.utils

import androidx.room.TypeConverter
import com.chrisy.bookmark.data.BookStatus

class Converters {

    @TypeConverter
    fun fromStatus(status: BookStatus): String = status.name

    @TypeConverter
    fun toStatus(value: String): BookStatus = BookStatus.valueOf(value)

}
