package com.yana.shuffler.models

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yana.shuffler.contracts.AddedBook
import com.yana.shuffler.models.room.BookDao
import com.yana.shuffler.models.room.DateDao

class AddedBookModel(private val bookDao: BookDao, private val dateDao: DateDao) : AddedBook.Model{
    private val uid = Firebase.auth.currentUser!!.uid
    override fun getAllBooks(onFinishedListener: AddedBook.Model.OnFinishedListener) {
        val list = bookDao.getAllBookInList(uid)
        onFinishedListener.gettingAllBooksListener(list)
    }

    override fun requestToDeleteBook(
        id: Int,
        onFinishedListener: AddedBook.Model.OnFinishedListener
    ) {
        val checkShuffledList = dateDao.checkIfTableExists(uid)
        val item = bookDao.getBook(id)

        if(!checkShuffledList){
            if(item!=null){
                onFinishedListener.processDeleteBook(item)
            }
        } else {
            onFinishedListener.deleteBookResult("Can't delete item")
        }
    }

    override fun deleteBook(id: Int, onFinishedListener: AddedBook.Model.OnFinishedListener) {
        bookDao.deleteBook(id)
        onFinishedListener.deleteBookResult("Deleted Successfully")
    }

    override fun requestToDeleteAllBooks(onFinishedListener: AddedBook.Model.OnFinishedListener) {
        val bookTable = bookDao.checkIfTableExists(uid)
        val dateTable = dateDao.checkIfTableExists(uid)

        if(bookTable || dateTable){
            onFinishedListener.processDeleteAll()
        } else {
            onFinishedListener.deleteAll("No data to delete")
        }
    }

    override fun deleteAllBooks(onFinishedListener: AddedBook.Model.OnFinishedListener) {
        dateDao.deleteAllDates(uid)
        bookDao.deleteAllBooks(uid)

        onFinishedListener.deleteAll("Deleted Successfully")
    }
}
