package com.yana.shuffler.contracts

import android.content.Context
import com.yana.shuffler.models.room.RoomDate
import java.util.Date

interface CalendarContract {
    interface Model{
        interface OnFinishCalendarListener{
            fun finishedGettingCalendarData(dataForAdapter: ArrayList<RoomDate>)
            fun finishedGettingDateTableData(result :Boolean)
            fun canBeOpened(bookId: Int)
            fun cannotBeOpened()
        }

        fun getCalendarData(date: Date, dayInMonth: Int, context: Context, calendarListener: OnFinishCalendarListener)
        fun getDateTableData(context: Context, calendarListener: OnFinishCalendarListener)
        fun shuffleRetrievedData(daysAfter: Int, context: Context, calendarListener: OnFinishCalendarListener)
        fun checkIfBookCanBeOpened(dateToday: String, bookDateId: Int, context: Context, calendarListener: OnFinishCalendarListener)
    }

    interface View{
        fun setUpShuffleDialog(result: Boolean)
        fun setUpCalendarView(dataForAdapter: ArrayList<RoomDate>)
        fun displayBookForTheDay(bookId: Int)
        fun onCantBeOpened()
    }

    interface Presenter{
        fun requestCalendarData(date: Date, dayInMonth: Int, context: Context)
        fun requestDateTableData(context: Context)
        fun checkIfBookCanBeOpened(dateToday: String, bookDateId: Int, context: Context)
        fun shuffleList(daysAfter: Int, context: Context)
    }
}