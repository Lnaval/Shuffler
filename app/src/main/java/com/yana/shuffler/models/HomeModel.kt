package com.yana.shuffler.models

import android.content.Context
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yana.shuffler.BookQueryResult
import com.yana.shuffler.contracts.HomeContract
import com.yana.shuffler.models.room.AddedBookDatabase

class HomeModel: HomeContract.Model {
    private val uid = Firebase.auth.currentUser!!.uid
    override fun getBookDataByDate(
        context: Context,
        dateToday: String,
        homeListener: HomeContract.Model.HomeListener
    ) {
        val checkBookStatus = AddedBookDatabase.getInstance(context).dateDao().getBookStatus(false, uid)
        val checkBookDateTable = AddedBookDatabase.getInstance(context).dateDao().checkIfTableExists(uid)
        val tableExist = AddedBookDatabase.getInstance(context).bookDao().checkIfTableExists(uid)
        val bookData = AddedBookDatabase.getInstance(context).bookDao().getTodayBook(dateToday, uid)



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
        val data = AddedBookDatabase.getInstance(context).bookDao().getFiveBooks(uid)
        homeListener.fiveBooksResult(data)
    }

    override fun deleteShelf(context: Context) {
        AddedBookDatabase.getInstance(context).bookDao().deleteAllBooks(uid)
        AddedBookDatabase.getInstance(context).dateDao().deleteAllDates(uid)
    }
}