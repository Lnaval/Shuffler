package com.yana.shuffler.presenters

import android.content.Context
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

    override fun bookAlreadyReadRsult(message: String) {
        mainView!!.displayAlreadyReadMessage(message)
    }

    override fun requestBookDataByDateToday(context: Context, dateToday: String) {
        model.getBookDataByDate(context, dateToday, this)
    }

    override fun requestFiveBooks(context: Context) {
        model.getFiveBooks(context, this)
    }
}