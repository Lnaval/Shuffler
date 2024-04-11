package com.yana.shuffler.contracts

import android.content.Context
import com.yana.shuffler.models.room.RoomDate

interface CalendarContract {
    interface Model{
        interface OnFinishCalendarListener{
            fun finishedGettingCalendarData(dataForAdapter: ArrayList<RoomDate>)
            fun finishedGettingDateTableData(result :Boolean)
        }

        fun getCalendarData(month: String, dayInMonth: Int, context: Context, calendarListener: OnFinishCalendarListener)
        fun getDateTableData(context: Context, calendarListener: OnFinishCalendarListener)
        fun shuffleRetrievedData(daysAfter: Int, context: Context, calendarListener: OnFinishCalendarListener)
    }

    interface View{
        fun setUpShuffleDialog(result: Boolean)
        fun setUpCalendarView(dataForAdapter: ArrayList<RoomDate>)
    }

    interface Presenter{
        fun requestCalendarData(month: String, dayInMonth: Int, context: Context)
        fun requestDateTableData(context: Context)
        fun shuffleList(daysAfter: Int, context: Context)
    }
}