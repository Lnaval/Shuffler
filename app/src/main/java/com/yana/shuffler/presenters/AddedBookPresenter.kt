package com.yana.shuffler.presenters

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

    override fun getAllBooks() {
        model.getAllBooks(this)
    }

    override fun requestDelete(id: Int) {
        model.requestToDeleteBook(id, this)
    }

    override fun confirmDelete(id: Int) {
        model.deleteBook(id, this)
    }

    override fun requestDeleteAll() {
        model.requestToDeleteAllBooks(this)
    }

    override fun confirmDeleteAll() {
        model.deleteAllBooks(this)
    }
}