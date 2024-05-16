package com.yana.shuffler.contracts

import com.yana.shuffler.models.enumclasses.CalendarResult
import com.yana.shuffler.models.room.RoomDate
import java.util.Date

interface CalendarContract {
    interface Model{
        fun getCalendarData(date: Date, dayInMonth: Int, uid: String) : ArrayList<RoomDate>
        fun getDateTableData(uid: String) : CalendarResult
        fun shuffleRetrievedData(daysAfter: String, uid: String) : String
        fun checkIfBookCanBeOpened(dateToday: String, bookDateId: Int, uid: String) : Int
    }

    interface View{
        fun setUpShuffleDialog(result: Boolean)
        fun setUpCalendarView(dataForAdapter: ArrayList<RoomDate>)
        fun displayBookForTheDay(bookId: Int)
        fun resultDisplay(message: String)
        fun displayMessageWhenBookIsEmpty()
    }

    interface Presenter{
        fun onViewCalendarReady(date: Date, dayInMonth: Int, uid: String)
        fun checkCalendarData(uid: String)
        fun onClickDate(dateToday: String, bookDateId: Int, uid: String)
        fun onConfirmShuffle(daysAfter: String, uid: String)
    }
}