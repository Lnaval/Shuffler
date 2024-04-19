package com.yana.shuffler.contracts

import android.content.Context
import com.yana.shuffler.models.room.RoomBook

interface AddedBook {
    interface Model{
        interface OnFinishedListener{
            fun gettingAllBooksListener(books : List<RoomBook>)
            fun processDeleteBook(book: RoomBook)
            fun deleteBookResult(result: String)
            fun processDeleteAll()
            fun deleteAll(result: String)
        }

        fun getAllBooks(context: Context, onFinishedListener: OnFinishedListener)
        fun requestToDeleteBook(context: Context, id: Int, onFinishedListener: OnFinishedListener)
        fun deleteBook(context: Context, id: Int, onFinishedListener: OnFinishedListener)
        fun requestToDeleteAllBooks(context: Context, onFinishedListener: OnFinishedListener)
        fun deleteAllBooks(context: Context, onFinishedListener: OnFinishedListener)
    }

    interface Presenter{
        fun getAllBooks(context: Context)
        fun requestDelete(context: Context, id: Int)
        fun confirmDelete(context: Context, id: Int)
        fun requestDeleteAll(context: Context)
        fun confirmDeleteAll(context: Context)
    }

    interface View{
        fun setUpAddedBooksAdapter(books: List<RoomBook>)
        fun confirmToDeleteBookDisplay(book: RoomBook)
        fun displayDeleteResult(result: String)

        fun confirmToDeleteAllBooks()
    }
}