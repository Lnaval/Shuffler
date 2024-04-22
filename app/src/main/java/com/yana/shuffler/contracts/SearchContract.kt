package com.yana.shuffler.contracts

import com.yana.shuffler.models.Book

interface SearchContract {
    interface Model{
        interface OnFinishedSearchListener{
            fun onFinishSearch(books: ArrayList<Book>)
            fun onBookAdded(result: String)
            fun onFinishSearchMore(books: ArrayList<Book>)
            fun onViewBook(
                title: String,
                author: String,
                firstYearPublished: String,
                image: String?,
                subjects: String
            )

            fun searchError()

            fun shuffledListExists()
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
            searchListener: OnFinishedSearchListener)
        fun getMoreBooks(
            searchKey: String, pageNumber: Int,
            searchListener: OnFinishedSearchListener)
        fun viewBook(
            book: Book,
            searchListener: OnFinishedSearchListener)

        fun doesShuffledListExist(searchListener: OnFinishedSearchListener)
    }

    interface Presenter{
        fun searchBooks(searchKey: String, pageNumber: Int)
        fun addBook(
            title: String,
            author: String,
            firstYearPublished: String,
            image: String?,
            subjects: String)
        fun searchMore(searchKey: String, pageNumber: Int)
        fun viewBook(book: Book)
        fun checkShuffledList()
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