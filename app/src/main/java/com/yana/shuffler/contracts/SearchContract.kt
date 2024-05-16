package com.yana.shuffler.contracts

import com.yana.shuffler.models.Book

interface SearchContract {
    interface Model{
        interface OnFinishedSearchListener{
            fun onFinishSearch(books: ArrayList<Book>)
            fun onFinishSearchMore(books: ArrayList<Book>)

            fun searchError()

        }
        fun getBooks(
            searchKey: String,
            pageNumber: Int,
            searchListener: OnFinishedSearchListener)
        fun addBookToList(
            title: String,
            author: String,
            firstYearPublished: String,
            image: String?,
            subjects: String,
            uid: String) : Boolean
        fun getMoreBooks(
            searchKey: String, pageNumber: Int,
            searchListener: OnFinishedSearchListener)
        fun viewBook(
            book: Book,
            searchListener: OnFinishedSearchListener) : Book

        fun doesShuffledListExist(searchListener: OnFinishedSearchListener, uid: String) : Boolean
    }

    interface Presenter{
        fun onSearchBooks(searchKey: String, pageNumber: Int)
        fun addBook(
            title: String,
            author: String,
            firstYearPublished: String,
            image: String?,
            subjects: String,
            uid: String)
        fun onSearchMore(searchKey: String, pageNumber: Int)
        fun onViewBook(book: Book)
        fun checkShuffledList(uid: String)
    }

    interface View{
        fun setUpSearchedItemsList(books: ArrayList<Book>)
        fun notifyAddedBookResult(result: String)
        fun addMoreBookResults(books: ArrayList<Book>)
        fun setUpDialogBottomSheetBookDetails(
            title: String,
            author: String,
            firstYearPublished: String,
            image: String?,
            subjects: String
        )
        fun displayWhenShuffledListExists()
        fun displaySearchError()
    }
}