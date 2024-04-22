package com.yana.shuffler.presenters

import com.yana.shuffler.contracts.AddedBook

class AddedBookPresenter(
    private var mainView: AddedBook.View?,
    private var model: AddedBook.Model
) : AddedBook.Presenter{
    override fun onViewReady(uid: String) {
        val books = model.getAllBooks(uid)
        mainView!!.setUpAddedBooksAdapter(books)
    }

    override fun onDeleteClicked(id: Int, uid: String) {
        val isPossible = model.requestToDeleteBook(id, uid)
        if(isPossible){
            mainView!!.confirmToDeleteBookDisplay(id)
        } else {
            mainView!!.displayDeleteResult("Can't delete item")
        }
    }

    override fun onConfirmDeletionClicked(id: Int) {
        model.deleteBook(id)
        mainView!!.displayDeleteResult("Deleted Successfully")
    }

    override fun onDeleteAllClicked(uid: String) {
        val isPossible = model.requestToDeleteAllBooks(uid)
        if(isPossible){
            mainView!!.confirmToDeleteAllBooks()
        }
    }

    override fun onConfirmDeleteAllClicked(uid: String) {
        model.deleteAllBooks(uid)
        mainView!!.displayDeleteResult("Deleted Successfully")
    }
}