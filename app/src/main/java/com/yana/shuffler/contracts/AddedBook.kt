package com.yana.shuffler.contracts

import com.yana.shuffler.models.room.RoomBook

interface AddedBook {
    interface Model{

        fun getAllBooks(uid: String) : List<RoomBook>
        fun requestToDeleteBook(id: Int, uid: String) : Boolean
        fun deleteBook(id: Int) : String
        fun requestToDeleteAllBooks(uid: String) : Boolean
        fun deleteAllBooks(uid: String)
    }

    interface Presenter{
        fun onViewReady(uid: String)
        fun onDeleteClicked(id: Int, uid: String)
        fun onConfirmDeletionClicked(id: Int)
        fun onDeleteAllClicked(uid: String)
        fun onConfirmDeleteAllClicked(uid: String)
    }

    interface View{
        fun setUpAddedBooksAdapter(books: List<RoomBook>)
        fun confirmToDeleteBookDisplay(id: Int)
        fun displayDeleteResult(result: String)

        fun confirmToDeleteAllBooks()
    }
}