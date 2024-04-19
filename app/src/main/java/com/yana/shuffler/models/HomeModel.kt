package com.yana.shuffler.models

import android.content.Context
import com.yana.shuffler.BookQueryResult
import com.yana.shuffler.contracts.HomeContract
import com.yana.shuffler.models.room.AddedBookDatabase

class HomeModel: HomeContract.Model {
    override fun getBookDataByDate(
        context: Context,
        dateToday: String,
        homeListener: HomeContract.Model.HomeListener
    ) {
        val checkBookStatus = AddedBookDatabase.getInstance(context).dateDao().getBookStatus(false)
        val checkBookDateTable = AddedBookDatabase.getInstance(context).dateDao().checkIfTableExists()
        val tableExist = AddedBookDatabase.getInstance(context).bookDao().checkIfTableExists()
        val bookData = AddedBookDatabase.getInstance(context).bookDao().getTodayBook(dateToday)



        if(tableExist){
            if(!checkBookStatus && checkBookDateTable){
                homeListener.bookAlreadyReadRsult(BookQueryResult.Completed)
            } else {
                if(bookData!=null){
                    homeListener.bookDataByDateResult(bookData)
                } else {
                    homeListener.bookAlreadyReadRsult(BookQueryResult.OnTrack)
                }
            }
        } else {
            homeListener.bookAlreadyReadRsult(BookQueryResult.Empty)
        }
    }

    override fun getFiveBooks(context: Context, homeListener: HomeContract.Model.HomeListener) {
        val data = AddedBookDatabase.getInstance(context).bookDao().getFiveBooks()
        homeListener.fiveBooksResult(data)
    }
}