package com.yana.shuffler.presenters

import com.yana.shuffler.BookQueryResult
import com.yana.shuffler.contracts.HomeContract
import com.yana.shuffler.models.room.RoomBook

class HomePresenter (
    private var mainView: HomeContract.View?,
    private val model: HomeContract.Model
) : HomeContract.Presenter, HomeContract.Model.HomeListener {
    override fun bookDataByDateResult(book: RoomBook) {
        mainView!!.displayRetrievedBook(book)
    }

    override fun fiveBooksResult(books: List<RoomBook>) {
        mainView!!.displayRetrievedFiveBooks(books)
    }

    override fun bookAlreadyReadRsult(result: BookQueryResult) {
        mainView!!.displayAlreadyReadMessage(result)
    }

    override fun requestBookDataByDateToday(dateToday: String) {
        model.getBookDataByDate(dateToday, this)
    }

    override fun requestFiveBooks() {
        model.getFiveBooks(this)
    }

    override fun requestDeleteShelf() {
        model.deleteShelf()
    }
}