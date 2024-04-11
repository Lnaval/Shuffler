package com.yana.shuffler.presenters

import android.content.Context
import com.yana.shuffler.contracts.CalendarContract
import com.yana.shuffler.models.room.RoomDate

class CalendarPresenter(
    private var mainView: CalendarContract.View?,
    private val model: CalendarContract.Model
) : CalendarContract.Presenter, CalendarContract.Model.OnFinishListener{
    override fun gettingCalendarData(dataForAdapter: ArrayList<RoomDate>) {
        mainView!!.setUpCalendarView(dataForAdapter)
    }

    override fun gettingDateTableData(result: Boolean) {
        mainView!!.setUpShuffleDialog(result)
    }

    override fun requestCalendarData(month: String, dayInMonth: Int, context: Context) {
        model.getCalendarData(month, dayInMonth, context, this)
    }

    override fun requestDateTableData(context: Context) {
        model.getDateTableData(context, this)
    }

    override fun shuffleList(daysAfter: Int, context: Context) {
        model.shuffleRetrievedData(daysAfter, context, this)
    }

}