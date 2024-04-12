package com.yana.shuffler.models.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun addBook(book: RoomBook)

    @Delete
    fun deleteBook(book: RoomBook)

    @Query("SELECT * FROM book_table")
    fun getAllBookInList(): List<RoomBook>

    @Query("SELECT * FROM book_table WHERE id LIKE :bookId")
    fun getBook(bookId: Int): RoomBook
}
