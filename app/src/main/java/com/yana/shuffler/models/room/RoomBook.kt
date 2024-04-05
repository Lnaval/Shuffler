package com.yana.shuffler.models.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_table")
data class RoomBook (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") val title: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "author") val author: String
)
