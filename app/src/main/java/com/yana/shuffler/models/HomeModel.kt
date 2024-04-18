package com.yana.shuffler.models

import android.content.Context
import com.yana.shuffler.contracts.HomeContract
import com.yana.shuffler.models.room.AddedBookDatabase

class HomeModel: HomeContract.Model {
    override fun getBookDataByDate(
        context: Context,
        dateToday: String,
        homeListener: HomeContract.Model.HomeListener
    ) {
        val bookData = AddedBookDatabase.getInstance(context).bookDao().getTodayBook(dateToday)

        if(bookData!=null){
            homeListener.bookDataByDateResult(bookData)
        } else {
            homeListener.bookAlreadyReadRsult("You're currently on track with your reading list")
        }
    }

    override fun getFiveBooks(context: Context, homeListener: HomeContract.Model.HomeListener) {
        val data = AddedBookDatabase.getInstance(context).bookDao().getFiveBooks()
        homeListener.fiveBooksResult(data)
    }
}