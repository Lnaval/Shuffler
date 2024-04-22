package com.yana.shuffler.presenters

import com.yana.shuffler.contracts.SearchContract
import com.yana.shuffler.models.Book

class SearchPresenter(
    private var mainView: SearchContract.View?,
    private val model: SearchContract.Model
): SearchContract.Presenter, SearchContract.Model.OnFinishedSearchListener {
    override fun onSearchBooks(searchKey: String, pageNumber: Int) {
        model.getBooks(searchKey, pageNumber, this)
    }

    override fun addBook(
        title: String,
        author: String,
        firstYearPublished: String,
        image: String?,
        subjects: String,
        uid: String
    ) {
        val canBeAdded = model.addBookToList(title, author, firstYearPublished, image, subjects, uid)
        if(canBeAdded){
            mainView!!.notifyAddedBookResult("Book added")
        } else {
            mainView!!.notifyAddedBookResult("Book already added")
        }
    }

    override fun onSearchMore(searchKey: String, pageNumber: Int) {
        model.getMoreBooks(searchKey, pageNumber,this)
    }

    override fun onViewBook(book: Book) {
        val item = model.viewBook(book, this)

        val author = if(item.author.isEmpty()) "Not Found" else book.author.toString()
        val year = if(item.firstPublishYear == null) "Not Found" else item.firstPublishYear.toString()

        mainView!!.setUpDialogBottomSheetBookDetails(item.title, author, year, item.image, item.subject.toString())
    }

    override fun checkShuffledList(uid: String) {
        val check = model.doesShuffledListExist(this, uid)
        if(check){
            mainView!!.displayWhenShuffledListExists()
        }
    }

    override fun onFinishSearch(books: ArrayList<Book>) {
        mainView!!.setUpSearchedItemsList(books)
    }

    override fun onFinishSearchMore(books: ArrayList<Book>) {
        mainView!!.addMoreBookResults(books)
    }

    override fun searchError() {
        mainView!!.displaySearchError()
    }
}
