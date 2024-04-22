package com.yana.shuffler.models

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yana.shuffler.contracts.AddedBook
import com.yana.shuffler.models.room.BookDao
import com.yana.shuffler.models.room.DateDao
import com.yana.shuffler.models.room.RoomBook

class AddedBookModel(private val bookDao: BookDao, private val dateDao: DateDao) : AddedBook.Model{
    override fun getAllBooks(uid: String): List<RoomBook> {
        return bookDao.getAllBookInList(uid)
    }

    override fun requestToDeleteBook(id: Int, uid: String): Boolean {
        val checkShuffledList = dateDao.checkIfTableExists(uid)
        val item = bookDao.getBook(id)

        if(!checkShuffledList){
            if(item!=null){
                return true
            }
        }
        return false
    }

    override fun deleteBook(id: Int): String {
        bookDao.deleteBook(id)
        return "Deleted Successfully"
    }

    override fun requestToDeleteAllBooks(uid: String): Boolean {
        val bookTable = bookDao.checkIfTableExists(uid)
        val dateTable = dateDao.checkIfTableExists(uid)

        return bookTable || dateTable
    }

    override fun deleteAllBooks(uid: String) {
        dateDao.deleteAllDates(uid)
        bookDao.deleteAllBooks(uid)
    }
}
