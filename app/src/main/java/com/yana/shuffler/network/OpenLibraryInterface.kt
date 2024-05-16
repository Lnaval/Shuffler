package com.yana.shuffler.network

import com.yana.shuffler.models.Books
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenLibraryInterface {
    @GET("search.json")
    fun searchBooks(
        @Query("title") title: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int = 5): Call<Books>
}