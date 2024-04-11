package com.yana.shuffler.contracts

import android.content.Context
import com.yana.shuffler.models.room.RoomDate

interface CalendarContract {
    interface Model{
        interface OnFinishListener{
            fun gettingCalendarData(dataForAdapter: ArrayList<RoomDate>)
            fun gettingDateTableData(result :Boolean)
        }

        fun getCalendarData(month: String, dayInMonth: Int, context: Context, onFinishListener: OnFinishListener)
        fun getDateTableData(context: Context, onFinishListener: OnFinishListener)
        fun shuffleRetrievedData(daysAfter: Int, context: Context, onFinishListener: OnFinishListener)
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