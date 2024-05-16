package com.yana.shuffler.contracts

import com.yana.shuffler.models.room.RoomBook

interface ShowBookOnDateContract {
    interface Model{
        interface OnFinishLoadBookOnDateDataListener{
            fun loadedBookOnDateDate(data: RoomBook, bookStatus: Boolean)
            fun updateBookStatusResult(result: Boolean)
            fun completedAllBooks(message: String)
        }

        fun loadBookOnDateData(id: Int, bookOnDateListener: OnFinishLoadBookOnDateDataListener, uid: String)
        fun updateBookStatus(dateId: Int, bookOnDateListener: OnFinishLoadBookOnDateDataListener, uid: String)
        fun deleteBookShelf(bookOnDateListener: OnFinishLoadBookOnDateDataListener, uid: String)
    }

    interface View{
        fun displayLoadedData(data: RoomBook, bookStatus: Boolean)
        fun displayUpdatedBookStatus(result: Boolean)
        fun displayFinishedShelf(message: String)
    }

    interface Presenter{
        fun onClickDateOnCalendar(id: Int, uid: String)
        fun onClickUpdateStatus(dateId: Int, uid: String)
        fun onClickDeleteShelf(uid: String)
    }
}