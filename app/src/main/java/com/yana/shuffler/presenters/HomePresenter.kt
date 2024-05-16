package com.yana.shuffler.presenters

import com.yana.shuffler.models.enumclasses.BookQueryResult
import com.yana.shuffler.contracts.HomeContract
import com.yana.shuffler.models.room.RoomBook

class HomePresenter (
    private var mainView: HomeContract.View?,
    private val model: HomeContract.Model
) : HomeContract.Presenter, HomeContract.Model.HomeListener {
    override fun bookDataByDateResult(book: RoomBook) {
        mainView!!.displayRetrievedBook(book)
    }

    override fun bookStatusResult(result: BookQueryResult) {
        mainView!!.displayAlreadyReadMessage(result)
    }

    override fun requestBookDataByDateToday(dateToday: String, uid: String) {
        model.getBookDataByDate(dateToday, this, uid)
    }

    override fun requestFiveBooks(uid: String) {
        val books = model.getFiveBooks(uid)
        mainView!!.displayRetrievedFiveBooks(books)
    }

    override fun requestDeleteShelf(uid: String) {
        model.deleteShelf(uid)
    }
}