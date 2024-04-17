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

        bookOnDateListener.loadedBookOnDateDate(data)
    }

    override fun updateBookStatus(
        context: Context,
        dateId: Int,
        bookOnDateListener: ShowBookOnDateContract.Model.OnFinishLoadBookOnDateDataListener
    ) {
        val toUpdate = AddedBookDatabase.getInstance(context).dateDao().getBookDateByBookId(dateId)
        val updatedData = RoomDate(toUpdate.dateId, toUpdate.date, toUpdate.book, !toUpdate.isCompleted)

        AddedBookDatabase.getInstance(context).dateDao().updateBookStatus(updatedData)

        //check id update is successful
        bookOnDateListener.updateBookStatusResult(true)
    }
}