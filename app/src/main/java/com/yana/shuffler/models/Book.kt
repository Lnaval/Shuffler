package com.yana.shuffler.models

import com.google.gson.annotations.SerializedName

data class Book (
    val title: String,
    @SerializedName("cover_edition_key") val image: String,
    @SerializedName("author_name") val author: List<String>,
    @SerializedName("first_publish_year") val firstPublishYear: Int,
    val subject: List<String>
)

data class Books(
    @SerializedName("docs") val books: ArrayList<Book>
)
