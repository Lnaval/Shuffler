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

    @Query("SELECT * FROM date_table WHERE uid LIKE :uid")
    fun getAll(uid: String): List<RoomDate>

    @Query("SELECT * FROM date_table WHERE date_id LIKE :id AND uid LIKE :uid")
    fun getIndividual(id: Int, uid: String): RoomDate

//    @Query("SELECT * FROM date_table WHERE date <= :date AND uid LIKE :uid")
//    fun getTodayBook(date: String, uid: String): RoomDate

    @Query("SELECT * FROM date_table " +
            "INNER JOIN book_table ON book_table.id = date_table.book " +
            "WHERE book_table.id LIKE :id " +
            "AND date_table.uid LIKE :uid")
    fun getBookDateByBookId(id: Int, uid: String): RoomDate

//    @Query("SELECT * FROM date_table WHERE book LIKE :bookId AND uid LIKE :uid")
//    fun getDateWhereBookAssigned(bookId: Int, uid: String): RoomDate

    @Update
    fun updateBookStatus(roomDate: RoomDate)

    @Query("SELECT EXISTS(SELECT * FROM date_table WHERE uid LIKE :uid)")
    fun checkIfTableExists(uid: String) : Boolean

    @Query("SELECT EXISTS(SELECT * FROM date_table WHERE is_completed LIKE :status AND uid LIKE :uid)")
    fun getBookStatus(status: Boolean, uid: String): Boolean

    @Query("DELETE FROM date_table WHERE uid LIKE :uid")
    fun deleteAllDates(uid: String)
}