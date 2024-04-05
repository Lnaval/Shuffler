package com.yana.shuffler.models

import android.content.Context
import com.yana.shuffler.contracts.AddedBook
import com.yana.shuffler.models.room.AddedBookDatabase

class AddedBookModel : AddedBook.Model{
    override fun getAllBooks(
        context: Context,
        onFinishedListener: AddedBook.Model.OnFinishedListener
    ) {
        val list = AddedBookDatabase.getInstance(context).bookDao().getAllBookInList()
        onFinishedListener.gettingAllBooksListener(list)
    }

}
