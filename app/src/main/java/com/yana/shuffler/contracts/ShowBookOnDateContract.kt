package com.yana.shuffler.contracts

import com.yana.shuffler.models.room.RoomBook

interface ShowBookOnDateContract {
    interface Model{
        interface OnFinishLoadBookOnDateDataListener{
            fun loadedBookOnDateDate(data: RoomBook, bookStatus: Boolean)
            fun updateBookStatusResult(result: Boolean)
            fun completedAllBooks(message: String)
        }

        fun loadBookOnDateData(id: Int, bookOnDateListener: OnFinishLoadBookOnDateDataListener)
        fun updateBookStatus(dateId: Int, bookOnDateListener: OnFinishLoadBookOnDateDataListener)
        fun deleteBookShelf(bookOnDateListener: OnFinishLoadBookOnDateDataListener)
    }

    interface View{
        fun displayLoadedData(data: RoomBook, bookStatus: Boolean)
        fun displayUpdatedBookStatus(result: Boolean)
        fun displayFinishedShelf(message: String)
    }

    interface Presenter{
        fun requestBookOnDateData(id: Int)
        fun requestUpdateBookStatus(dateId: Int)
        fun requestDeleteShelf()
    }
}