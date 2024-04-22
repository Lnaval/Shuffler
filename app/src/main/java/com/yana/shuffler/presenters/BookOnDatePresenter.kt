package com.yana.shuffler.presenters

import com.yana.shuffler.contracts.ShowBookOnDateContract
import com.yana.shuffler.models.room.RoomBook

class BookOnDatePresenter(
    private var mainView: ShowBookOnDateContract.View?,
    private val model: ShowBookOnDateContract.Model
) : ShowBookOnDateContract.Presenter, ShowBookOnDateContract.Model.OnFinishLoadBookOnDateDataListener{
    override fun loadedBookOnDateDate(data: RoomBook, bookStatus: Boolean) {
        mainView!!.displayLoadedData(data, bookStatus)
    }

    override fun updateBookStatusResult(result: Boolean) {
        mainView!!.displayUpdatedBookStatus(result)
    }

    override fun completedAllBooks(message: String) {
        mainView!!.displayFinishedShelf(message)
    }

    override fun requestBookOnDateData(id: Int) {
        model.loadBookOnDateData(id, this)
    }

    override fun requestUpdateBookStatus(dateId: Int) {
        model.updateBookStatus(dateId, this)
    }

    override fun requestDeleteShelf() {
        model.deleteBookShelf(this)
    }
}