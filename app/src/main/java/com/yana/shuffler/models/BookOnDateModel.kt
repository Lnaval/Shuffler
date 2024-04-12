package com.yana.shuffler.models

import android.content.Context
import com.yana.shuffler.contracts.ShowBookOnDateContract
import com.yana.shuffler.models.room.AddedBookDatabase

class BookOnDateModel : ShowBookOnDateContract.Model {
    override fun loadBookOnDateData(
        context: Context,
        id: Int,
        bookOnDateListener: ShowBookOnDateContract.Model.OnFinishLoadBookOnDateDataListener
    ) {
        val data = AddedBookDatabase.getInstance(context).bookDao().getBook(id)

        bookOnDateListener.loadedBookOnDateDate(data)
    }
}