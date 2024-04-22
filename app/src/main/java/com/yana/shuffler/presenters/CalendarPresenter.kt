package com.yana.shuffler.presenters

import com.yana.shuffler.contracts.CalendarContract
import com.yana.shuffler.models.room.RoomDate
import java.util.Date

class CalendarPresenter(
    private var mainView: CalendarContract.View?,
    private val model: CalendarContract.Model
) : CalendarContract.Presenter, CalendarContract.Model.OnFinishCalendarListener{
    override fun finishedGettingCalendarData(dataForAdapter: ArrayList<RoomDate>) {
        mainView!!.setUpCalendarView(dataForAdapter)
    }

    override fun finishedGettingDateTableData(result: Boolean) {
        mainView!!.setUpShuffleDialog(result)
    }

    override fun canBookOnDateBeOpen(bookId: Int) {
        mainView!!.displayBookForTheDay(bookId)
    }

    override fun errorMessage(message: String) {
        mainView!!.onError(message)
    }

    override fun checkBookListSize() {
        mainView!!.displayMessageWhenBookIsEmpty()
    }

    override fun requestCalendarData(date: Date, dayInMonth: Int) {
        model.getCalendarData(date, dayInMonth, this)
    }

    override fun requestDateTableData() {
        model.getDateTableData(this)
    }

    override fun checkIfBookCanBeOpened(dateToday: String, bookDateId: Int) {
        model.checkIfBookCanBeOpened(dateToday, bookDateId, this)
    }

    override fun shuffleList(daysAfter: String) {
        model.shuffleRetrievedData(daysAfter, this)
    }

}