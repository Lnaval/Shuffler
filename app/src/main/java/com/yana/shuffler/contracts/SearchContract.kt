package com.yana.shuffler.contracts

import android.content.Context
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
                imageUrl: String,
                subjects: String
            )
        }
        fun getBooks(
            searchKey: String,
            pageNumber: Int,
            searchListener: OnFinishedSearchListener)
        fun addBookToList(
            title: String,
            author: String,
            firstYearPublished: String,
            imageUrl: String,
            subjects: String,
            context: Context,
            searchListener: OnFinishedSearchListener)
        fun getMoreBooks(
            searchKey: String, pageNumber: Int,
            searchListener: OnFinishedSearchListener)
        fun viewBook(
            book: Book,
            searchListener: OnFinishedSearchListener)
    }

    interface Presenter{
        fun searchBooks(searchKey: String, pageNumber: Int)
        fun addBook(
            title: String,
            author: String,
            firstYearPublished: String,
            imageUrl: String,
            subjects: String,
            context: Context)
        fun searchMore(searchKey: String, pageNumber: Int)
        fun viewBook(book: Book)
    }

    interface View{
        fun setUpSearchedItemsList(books: ArrayList<Book>)
        fun notifyAddedBookResult(result: String)
        fun addMoreBookResults(books: ArrayList<Book>)
        fun setUpDialogBottomSheetBookDetails(
            title: String,
            author: String,
            firstYearPublished: String,
            imageUrl: String,
            subjects: String
        )
        fun showNoInternetDialog()
    }
}