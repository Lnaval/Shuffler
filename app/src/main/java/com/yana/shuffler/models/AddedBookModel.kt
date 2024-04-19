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

    override fun requestToDeleteBook(
        context: Context,
        id: Int,
        onFinishedListener: AddedBook.Model.OnFinishedListener
    ) {
        val checkShuffledList = AddedBookDatabase.getInstance(context).dateDao().checkIfTableExists()
        val item = AddedBookDatabase.getInstance(context).bookDao().getBook(id)

        if(!checkShuffledList){
            if(item!=null){
                onFinishedListener.processDeleteBook(item)
            }
        } else {
            onFinishedListener.deleteBookResult("Can't delete item")
        }
    }

    override fun deleteBook(
        context: Context,
        id: Int,
        onFinishedListener: AddedBook.Model.OnFinishedListener
    ) {
        AddedBookDatabase.getInstance(context).bookDao().deleteBook(id)
        onFinishedListener.deleteBookResult("Deleted Successfully")
    }

    override fun requestToDeleteAllBooks(
        context: Context,
        onFinishedListener: AddedBook.Model.OnFinishedListener
    ) {
        val bookTable = AddedBookDatabase.getInstance(context).bookDao().checkIfTableExists()
        val dateTable = AddedBookDatabase.getInstance(context).dateDao().checkIfTableExists()

        if(bookTable || dateTable){
            onFinishedListener.processDeleteAll()
        } else {
            onFinishedListener.deleteAll("No data to delete")
        }
    }

    override fun deleteAllBooks(
        context: Context,
        onFinishedListener: AddedBook.Model.OnFinishedListener
    ) {
        AddedBookDatabase.getInstance(context).dateDao().deleteAllDates()
        AddedBookDatabase.getInstance(context).bookDao().deleteAllBooks()

        onFinishedListener.deleteAll("Deleted Successfully")
    }
}
