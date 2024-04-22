package com.yana.shuffler.contracts

import com.yana.shuffler.BookQueryResult
import com.yana.shuffler.models.room.RoomBook

interface HomeContract {
    interface Model{
        interface HomeListener{
            fun bookDataByDateResult(book: RoomBook)
            fun fiveBooksResult(books: List<RoomBook>)
            fun bookAlreadyReadRsult(result: BookQueryResult)
        }
        fun getBookDataByDate(dateToday: String, homeListener: HomeListener)
        fun getFiveBooks(homeListener: HomeListener)
        fun deleteShelf()
    }

    interface Presenter{
        fun requestBookDataByDateToday(dateToday: String)
        fun requestFiveBooks()
        fun requestDeleteShelf()
    }

    interface View{
        fun displayRetrievedBook(book: RoomBook)
        fun displayRetrievedFiveBooks(books: List<RoomBook>)
        fun displayAlreadyReadMessage(message: BookQueryResult)
    }
}