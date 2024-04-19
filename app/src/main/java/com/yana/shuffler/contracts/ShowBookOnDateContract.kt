package com.yana.shuffler.contracts

import android.content.Context
import com.yana.shuffler.models.room.RoomBook

interface ShowBookOnDateContract {
    interface Model{
        interface OnFinishLoadBookOnDateDataListener{
            fun loadedBookOnDateDate(data: RoomBook, bookStatus: Boolean)
            fun updateBookStatusResult(result: Boolean)
            fun completedAllBooks(message: String)
        }

        fun loadBookOnDateData(context: Context, id: Int, bookOnDateListener: OnFinishLoadBookOnDateDataListener)
        fun updateBookStatus(context: Context, dateId: Int, bookOnDateListener: OnFinishLoadBookOnDateDataListener)
    }

    interface View{
        fun displayLoadedData(data: RoomBook, bookStatus: Boolean)
        fun displayUpdatedBookStatus(result: Boolean)
        fun displayFinishedShelf(message: String)
    }

    interface Presenter{
        fun requestBookOnDateData(context: Context, id: Int)
        fun requestUpdateBookStatus(context: Context, dateId: Int)
    }
}