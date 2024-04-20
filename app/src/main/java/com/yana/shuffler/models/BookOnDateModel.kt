package com.yana.shuffler.models

import android.content.Context
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yana.shuffler.contracts.ShowBookOnDateContract
import com.yana.shuffler.models.room.AddedBookDatabase
import com.yana.shuffler.models.room.RoomDate

class BookOnDateModel : ShowBookOnDateContract.Model {
    private val uid = Firebase.auth.currentUser!!.uid
    override fun loadBookOnDateData(
        context: Context,
        id: Int,
        bookOnDateListener: ShowBookOnDateContract.Model.OnFinishLoadBookOnDateDataListener
    ) {
        val data = AddedBookDatabase.getInstance(context).bookDao().getBook(id)
        val bookStatus = AddedBookDatabase.getInstance(context).dateDao().getBookDateByBookId(data.id, uid)
        bookOnDateListener.loadedBookOnDateDate(data, bookStatus.isCompleted)

    }

    override fun updateBookStatus(
        context: Context,
        dateId: Int,
        bookOnDateListener: ShowBookOnDateContract.Model.OnFinishLoadBookOnDateDataListener
    ) {
        val toUpdate = AddedBookDatabase.getInstance(context).dateDao().getBookDateByBookId(dateId, uid)
        val updatedData = RoomDate(toUpdate.dateId, toUpdate.date, toUpdate.book, !toUpdate.isCompleted, uid)

        val dateDao = AddedBookDatabase.getInstance(context).dateDao()
        dateDao.updateBookStatus(updatedData)
        val checkBookshelf = dateDao.getBookStatus(false, uid)
        bookOnDateListener.updateBookStatusResult(updatedData.isCompleted)

        if(!checkBookshelf){
            bookOnDateListener.completedAllBooks("You have completed all books in your bookshelf")
        }
    }

    override fun deleteBookShelf(
        context: Context,
        bookOnDateListener: ShowBookOnDateContract.Model.OnFinishLoadBookOnDateDataListener
    ) {
        AddedBookDatabase.getInstance(context).dateDao().deleteAllDates(uid)
        AddedBookDatabase.getInstance(context).bookDao().deleteAllBooks(uid)

        //bookOnDateListener.deleteBookShelf("Deleted Successfully")
    }
}