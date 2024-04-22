package com.yana.shuffler.contracts

import com.yana.shuffler.models.room.RoomDate
import java.util.Date

interface CalendarContract {
    interface Model{
        interface OnFinishCalendarListener{
            fun finishedGettingCalendarData(dataForAdapter: ArrayList<RoomDate>)
            fun finishedGettingDateTableData(result :Boolean)
            fun canBookOnDateBeOpen(bookId: Int)
            fun errorMessage(message: String)
            fun checkBookListSize()
        }

        fun getCalendarData(date: Date, dayInMonth: Int, calendarListener: OnFinishCalendarListener)
        fun getDateTableData(calendarListener: OnFinishCalendarListener)
        fun shuffleRetrievedData(daysAfter: String, calendarListener: OnFinishCalendarListener)
        fun checkIfBookCanBeOpened(dateToday: String, bookDateId: Int, calendarListener: OnFinishCalendarListener)
    }

    interface View{
        fun setUpShuffleDialog(result: Boolean)
        fun setUpCalendarView(dataForAdapter: ArrayList<RoomDate>)
        fun displayBookForTheDay(bookId: Int)
        fun onError(message: String)
        fun displayMessageWhenBookIsEmpty()
    }

    interface Presenter{
        fun requestCalendarData(date: Date, dayInMonth: Int)
        fun requestDateTableData()
        fun checkIfBookCanBeOpened(dateToday: String, bookDateId: Int)
        fun shuffleList(daysAfter: String)
    }
}