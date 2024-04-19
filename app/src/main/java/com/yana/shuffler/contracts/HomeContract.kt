package com.yana.shuffler.contracts

import android.content.Context
import com.yana.shuffler.BookQueryResult
import com.yana.shuffler.models.room.RoomBook

interface HomeContract {
    interface Model{
        interface HomeListener{
            fun bookDataByDateResult(book: RoomBook)
            fun fiveBooksResult(books: List<RoomBook>)
            fun bookAlreadyReadRsult(result: BookQueryResult)
        }
        fun getBookDataByDate(context: Context, dateToday: String, homeListener: HomeListener)
        fun getFiveBooks(context: Context, homeListener: HomeListener)
    }

    interface Presenter{
        fun requestBookDataByDateToday(context: Context, dateToday: String)
        fun requestFiveBooks(context: Context)
    }

    interface View{
        fun displayRetrievedBook(book: RoomBook)
        fun displayRetrievedFiveBooks(books: List<RoomBook>)
        fun displayAlreadyReadMessage(message: BookQueryResult)
    }
}