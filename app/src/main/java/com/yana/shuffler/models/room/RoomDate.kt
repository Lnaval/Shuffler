package com.yana.shuffler.models.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "date_table")
data class RoomDate(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "book") val book: Int,
    @ColumnInfo(name = "is_completed") val isCompleted: Boolean
)