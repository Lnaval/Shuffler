package com.yana.shuffler.presenters

import android.content.Context
import com.yana.shuffler.contracts.ShowBookOnDateContract
import com.yana.shuffler.models.room.RoomBook

class BookOnDatePresenter(
    private var mainView: ShowBookOnDateContract.View?,
    private val model: ShowBookOnDateContract.Model
) : ShowBookOnDateContract.Presenter, ShowBookOnDateContract.Model.OnFinishLoadBookOnDateDataListener{
    override fun loadedBookOnDateDate(data: RoomBook) {
        mainView!!.displayLoadedData(data)
    }

    override fun updateBookStatusResult(result: Boolean) {
        mainView!!.displayUpdatedBookStatus(result)
    }

    override fun requestBookOnDateData(context: Context, id: Int) {
        model.loadBookOnDateData(context, id, this)
    }

    override fun requestUpdateBookStatus(context: Context, dateId: Int) {
        model.updateBookStatus(context, dateId, this)
    }
}