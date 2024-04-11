package com.yana.shuffler.contracts

import android.content.Context
import com.yana.shuffler.models.Book

interface SearchContract {
    interface Model{
        interface OnFinishedSearchListener{
            fun onFinishSearch(books: ArrayList<Book>)
            fun onBookAdded()
            fun onFinishSearchMore(books: ArrayList<Book>)
        }
        fun getBooks(searchKey: String, pageNumber: Int, searchListener: OnFinishedSearchListener)
        fun addBookToList(book: Book, context: Context, searchListener: OnFinishedSearchListener)
        fun getMoreBooks(searchKey: String, pageNumber: Int, searchListener: OnFinishedSearchListener)
    }

    interface Presenter{
        fun searchBooks(searchKey: String, pageNumber: Int)
        fun addBook(book: Book, context: Context)
        fun searchMore(searchKey: String, pageNumber: Int)
    }

    interface View{
        fun setUpSearchedItemsList(books: ArrayList<Book>)
        fun notifyAddedBookResult()
        fun addMoreBookResults(books: ArrayList<Book>)
        fun showNoInternetDialog()
    }
}