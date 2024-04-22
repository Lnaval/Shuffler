package com.yana.shuffler.contracts

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

        fun getAllBooks(onFinishedListener: OnFinishedListener)
        fun requestToDeleteBook(id: Int, onFinishedListener: OnFinishedListener)
        fun deleteBook(id: Int, onFinishedListener: OnFinishedListener)
        fun requestToDeleteAllBooks(onFinishedListener: OnFinishedListener)
        fun deleteAllBooks(onFinishedListener: OnFinishedListener)
    }

    interface Presenter{
        fun getAllBooks()
        fun requestDelete(id: Int)
        fun confirmDelete(id: Int)
        fun requestDeleteAll()
        fun confirmDeleteAll()
    }

    interface View{
        fun setUpAddedBooksAdapter(books: List<RoomBook>)
        fun confirmToDeleteBookDisplay(book: RoomBook)
        fun displayDeleteResult(result: String)

        fun confirmToDeleteAllBooks()
    }
}