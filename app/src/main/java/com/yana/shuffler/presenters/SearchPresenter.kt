package com.yana.shuffler.presenters

import android.content.Context
import com.yana.shuffler.contracts.SearchContract
import com.yana.shuffler.models.Book

class SearchPresenter(
    private var mainView: SearchContract.View?,
    private val model: SearchContract.Model
): SearchContract.Presenter, SearchContract.Model.OnFinishedSearchListener {
    override fun checkConnectivity(context: Context) {
        val isOnline = model.isOnline(context)
        if(!isOnline)
            mainView!!.showNoInternetDialog()
    }

    override fun searchBooks(searchKey: String, pageNumber: Int) {
        model.getBooks(searchKey, pageNumber, this)
    }

    override fun addBook(book: Book, context: Context) {
        model.addBookToList(book, context, this)
    }

    override fun searchMore(searchKey: String, pageNumber: Int) {
        model.getMoreBooks(searchKey, pageNumber,this)
    }

    override fun onFinishSearch(books: ArrayList<Book>) {
        mainView!!.setUpSearchedItemsList(books)
    }

    override fun onBookAdded() {
        mainView!!.notifyAddedBookResult()
    }

    override fun onFinishSearchMore(books: ArrayList<Book>) {
        mainView!!.addMoreBookResults(books)
    }
}
