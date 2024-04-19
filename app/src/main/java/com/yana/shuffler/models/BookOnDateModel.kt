package com.yana.shuffler.models

import android.content.Context
import com.yana.shuffler.contracts.ShowBookOnDateContract
import com.yana.shuffler.models.room.AddedBookDatabase
import com.yana.shuffler.models.room.RoomDate

class BookOnDateModel : ShowBookOnDateContract.Model {
    override fun loadBookOnDateData(
        context: Context,
        id: Int,
        bookOnDateListener: ShowBookOnDateContract.Model.OnFinishLoadBookOnDateDataListener
    ) {
        val data = AddedBookDatabase.getInstance(context).bookDao().getBook(id)
        val bookStatus = AddedBookDatabase.getInstance(context).dateDao().getBookDateByBookId(data.id)
        bookOnDateListener.loadedBookOnDateDate(data, bookStatus.isCompleted)

    }

    override fun updateBookStatus(
        context: Context,
        dateId: Int,
        bookOnDateListener: ShowBookOnDateContract.Model.OnFinishLoadBookOnDateDataListener
    ) {
        val toUpdate = AddedBookDatabase.getInstance(context).dateDao().getBookDateByBookId(dateId)
        val updatedData = RoomDate(toUpdate.dateId, toUpdate.date, toUpdate.book, !toUpdate.isCompleted)

        val dateDao = AddedBookDatabase.getInstance(context).dateDao()
        dateDao.updateBookStatus(updatedData)
        val checkBookshelf = dateDao.getBookStatus(false)
        bookOnDateListener.updateBookStatusResult(updatedData.isCompleted)

        if(!checkBookshelf){
            bookOnDateListener.completedAllBooks("You have completed all books in your bookshelf")
        }
    }
}