package com.yana.shuffler.models.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DateDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addAll(list: ArrayList<RoomDate>)

    @Query("SELECT * FROM date_table")
    fun displayAll(): List<RoomDate>
}