package com.yana.shuffler.contracts

import com.yana.shuffler.BookQueryResult
import com.yana.shuffler.models.room.RoomBook

interface HomeContract {
    interface Model{
        interface HomeListener{
            fun bookDataByDateResult(book: RoomBook)
            fun bookStatusResult(result: BookQueryResult)
        }
        fun getBookDataByDate(dateToday: String, homeListener: HomeListener, uid: String)
        fun getFiveBooks(uid: String) : List<RoomBook>
        fun deleteShelf(uid: String)
    }

    interface Presenter{
        fun requestBookDataByDateToday(dateToday: String, uid: String)
        fun requestFiveBooks(uid: String)
        fun requestDeleteShelf(uid: String)
    }

    interface View{
        fun displayRetrievedBook(book: RoomBook)
        fun displayRetrievedFiveBooks(books: List<RoomBook>)
        fun displayAlreadyReadMessage(message: BookQueryResult)
    }
}