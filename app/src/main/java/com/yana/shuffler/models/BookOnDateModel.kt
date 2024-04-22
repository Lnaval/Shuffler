package com.yana.shuffler.models

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yana.shuffler.contracts.ShowBookOnDateContract
import com.yana.shuffler.models.room.BookDao
import com.yana.shuffler.models.room.DateDao
import com.yana.shuffler.models.room.RoomDate

class BookOnDateModel(private val bookDao: BookDao, private val dateDao: DateDao) : ShowBookOnDateContract.Model {
    private val uid = Firebase.auth.currentUser!!.uid
    override fun loadBookOnDateData(
        id: Int,
        bookOnDateListener: ShowBookOnDateContract.Model.OnFinishLoadBookOnDateDataListener
    ) {
        val data = bookDao.getBook(id)
        val bookStatus = dateDao.getBookDateByBookId(data.id, uid)
        bookOnDateListener.loadedBookOnDateDate(data, bookStatus.isCompleted)

    }

    override fun updateBookStatus(
        dateId: Int,
        bookOnDateListener: ShowBookOnDateContract.Model.OnFinishLoadBookOnDateDataListener
    ) {
        val toUpdate = dateDao.getBookDateByBookId(dateId, uid)
        val updatedData = RoomDate(toUpdate.dateId, toUpdate.date, toUpdate.book, !toUpdate.isCompleted, uid)

        dateDao.updateBookStatus(updatedData)
        val checkBookshelf = dateDao.getBookStatus(false, uid)
        bookOnDateListener.updateBookStatusResult(updatedData.isCompleted)

        if(!checkBookshelf){
            bookOnDateListener.completedAllBooks("You have completed all books in your bookshelf")
        }
    }

    override fun deleteBookShelf(bookOnDateListener: ShowBookOnDateContract.Model.OnFinishLoadBookOnDateDataListener) {
        dateDao.deleteAllDates(uid)
        bookDao.deleteAllBooks(uid)
    }
}