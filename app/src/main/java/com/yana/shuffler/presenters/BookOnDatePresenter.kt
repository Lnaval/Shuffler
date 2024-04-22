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

    override fun onClickDateOnCalendar(id: Int, uid: String) {
        model.loadBookOnDateData(id, this, uid)
    }

    override fun onClickUpdateStatus(dateId: Int, uid: String) {
        model.updateBookStatus(dateId, this, uid)
    }

    override fun onClickDeleteShelf(uid: String) {
        model.deleteBookShelf(this, uid)
    }
}