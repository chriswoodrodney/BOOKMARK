package com.chrisy.bookmark.network

import retrofit2.http.GET
import retrofit2.http.Query

// A minimal response model for book search
data class BookSearchResponse(
    val items: List<BookItem>
)

data class BookItem(
    val volumeInfo: VolumeInfo
)

data class VolumeInfo(
    val title: String?,
    val authors: List<String>?,
    val description: String?
)

interface BookApiService {
    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("key") apiKey: String
    ): BookSearchResponse
}