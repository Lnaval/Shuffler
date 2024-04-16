package com.yana.shuffler.presenters

import android.content.Context
import android.view.View
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

    override fun canBeOpened(bookId: Int, view: View) {
        mainView!!.displayBookForTheDay(bookId, view)
    }

    override fun cannotBeOpened() {
        mainView!!.onCantBeOpened()
    }

    override fun requestCalendarData(date: Date, dayInMonth: Int, context: Context) {
        model.getCalendarData(date, dayInMonth, context, this)
    }

    override fun requestDateTableData(context: Context) {
        model.getDateTableData(context, this)
    }

    override fun checkIfBookCanBeOpened(view: View, dateToday: String, bookDateId: Int, context: Context) {
        model.checkIfBookCanBeOpened(view, dateToday, bookDateId, context, this)
    }

    override fun shuffleList(daysAfter: Int, context: Context) {
        model.shuffleRetrievedData(daysAfter, context, this)
    }

}