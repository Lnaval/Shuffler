package com.yana.shuffler.presenters

import android.content.Context
import com.yana.shuffler.contracts.SearchContract
import com.yana.shuffler.models.Book

class SearchPresenter(
    private var mainView: SearchContract.View?,
    private val model: SearchContract.Model
): SearchContract.Presenter, SearchContract.Model.OnFinishedSearchListener {
    override fun searchBooks(searchKey: String, pageNumber: Int) {
        model.getBooks(searchKey, pageNumber, this)
    }

    override fun addBook(
        title: String,
        author: String,
        firstYearPublished: String,
        image: String?,
        subjects: String,
        context: Context
    ) {
        model.addBookToList(title, author, firstYearPublished, image, subjects, context, this)
    }

    override fun searchMore(searchKey: String, pageNumber: Int) {
        model.getMoreBooks(searchKey, pageNumber,this)
    }

    override fun viewBook(book: Book) {
        model.viewBook(book, this)
    }

    override fun checkShuffledList(context: Context) {
        model.doesShuffledListExist(context, this)
    }

    override fun onFinishSearch(books: ArrayList<Book>) {
        mainView!!.setUpSearchedItemsList(books)
    }

    override fun onBookAdded(result: String) {
        mainView!!.notifyAddedBookResult(result)
    }

    override fun onFinishSearchMore(books: ArrayList<Book>) {
        mainView!!.addMoreBookResults(books)
    }

    override fun onViewBook(
        title: String,
        author: String,
        firstYearPublished: String,
        image: String?,
        subjects: String
    ) {
        mainView!!.setUpDialogBottomSheetBookDetails(title, author, firstYearPublished, image, subjects)
    }

    override fun shuffledListExists() {
        mainView!!.displayWhenShuffledListExists()
    }
}
