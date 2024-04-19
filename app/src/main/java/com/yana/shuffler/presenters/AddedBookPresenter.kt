package com.yana.shuffler.presenters

import android.content.Context
import com.yana.shuffler.contracts.AddedBook
import com.yana.shuffler.models.room.RoomBook

class AddedBookPresenter(
    private var mainView: AddedBook.View?,
    private var model: AddedBook.Model
) : AddedBook.Presenter, AddedBook.Model.OnFinishedListener{
    override fun gettingAllBooksListener(books: List<RoomBook>) {
        mainView!!.setUpAddedBooksAdapter(books)
    }

    override fun processDeleteBook(book: RoomBook) {
        mainView!!.confirmToDeleteBookDisplay(book)
    }

    override fun deleteBookResult(result: String) {
        mainView!!.displayDeleteResult(result)
    }

    override fun processDeleteAll() {
        mainView!!.confirmToDeleteAllBooks()
    }

    override fun deleteAll(result: String) {
        mainView!!.displayDeleteResult(result)
    }

    override fun getAllBooks(context: Context) {
        model.getAllBooks(context, this)
    }

    override fun requestDelete(context: Context, id: Int) {
        model.requestToDeleteBook(context, id, this)
    }

    override fun confirmDelete(context: Context, id: Int) {
        model.deleteBook(context, id, this)
    }

    override fun requestDeleteAll(context: Context) {
        model.requestToDeleteAllBooks(context, this)
    }

    override fun confirmDeleteAll(context: Context) {
        model.deleteAllBooks(context, this)
    }
}