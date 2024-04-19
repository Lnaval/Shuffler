package com.yana.shuffler.models.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun addBook(book: RoomBook)

    @Query("DELETE FROM book_table WHERE id LIKE :id")
    fun deleteBook(id: Int)

    @Query("SELECT * FROM book_table")
    fun getAllBookInList(): List<RoomBook>

    @Query("SELECT * FROM book_table WHERE id LIKE :bookId")
    fun getBook(bookId: Int): RoomBook

    @Query("SELECT * FROM book_table LIMIT 5")
    fun getFiveBooks(): List<RoomBook>

    @Query("SELECT * FROM book_table " +
            "INNER JOIN date_table ON book_table.id = date_table.book " +
            "WHERE date_table.date <= :date AND date_table.is_completed LIKE 0")
    fun getTodayBook(date: String): RoomBook

    @Query("SELECT EXISTS(SELECT * FROM book_table WHERE name LIKE :bookName)")
    fun checkIfBookExists(bookName: String) : Boolean

    @Query("SELECT EXISTS(SELECT * FROM book_table)")
    fun checkIfTableExists(): Boolean

    @Query("DELETE FROM book_table")
    fun deleteAllBooks()
}
