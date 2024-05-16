package com.yana.shuffler.models.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RoomBook::class, RoomDate::class], version = 1)
abstract class AddedBookDatabase: RoomDatabase() {
    abstract fun bookDao() : BookDao
    abstract fun dateDao() :DateDao

    companion object{
        @Volatile
        private var INSTANCE: AddedBookDatabase? = null

        fun getInstance(context: Context): AddedBookDatabase{
            return INSTANCE?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext,
                    AddedBookDatabase::class.java,
                    "database")
                    .allowMainThreadQueries()
                    .build()
                INSTANCE =  instance
                instance
            }
        }
    }
}
