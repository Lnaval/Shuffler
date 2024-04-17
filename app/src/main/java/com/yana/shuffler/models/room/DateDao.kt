package com.yana.shuffler.models.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface DateDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addAll(list: ArrayList<RoomDate>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun add(date: RoomDate)

    @Query("SELECT * FROM date_table")
    fun getAll(): List<RoomDate>

    @Query("SELECT * FROM date_table WHERE date_id LIKE :id")
    fun getIndividual(id: Int): RoomDate

    @Query("SELECT * FROM date_table WHERE date <= :date")
    fun getTodayBook(date: String): RoomDate

    @Query("SELECT * FROM date_table " +
            "INNER JOIN book_table ON book_table.id = date_table.book " +
            "WHERE book_table.id LIKE :id")
    fun getBookDateByBookId(id: Int): RoomDate

    @Query("SELECT * FROM date_table WHERE book LIKE :bookId")
    fun getDateWhereBookAssigned(bookId: Int): RoomDate

    @Update
    fun updateBookStatus(roomDate: RoomDate)
}