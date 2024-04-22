package com.yana.shuffler.models

import com.yana.shuffler.contracts.ShowBookOnDateContract
import com.yana.shuffler.models.room.BookDao
import com.yana.shuffler.models.room.DateDao
import com.yana.shuffler.models.room.RoomDate

class BookOnDateModel(private val bookDao: BookDao, private val dateDao: DateDao) : ShowBookOnDateContract.Model {
    override fun loadBookOnDateData(
        id: Int,
        bookOnDateListener: ShowBookOnDateContract.Model.OnFinishLoadBookOnDateDataListener,
        uid: String
    ) {
        val data = bookDao.getBook(id)
        val bookStatus = dateDao.getBookDateByBookId(data.id, uid)
        bookOnDateListener.loadedBookOnDateDate(data, bookStatus.isCompleted)
    }

    override fun updateBookStatus(
        dateId: Int,
        bookOnDateListener: ShowBookOnDateContract.Model.OnFinishLoadBookOnDateDataListener, uid: String
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

    override fun deleteBookShelf(bookOnDateListener: ShowBookOnDateContract.Model.OnFinishLoadBookOnDateDataListener, uid: String) {
        dateDao.deleteAllDates(uid)
        bookDao.deleteAllBooks(uid)
    }
}