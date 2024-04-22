package com.yana.shuffler.models

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yana.shuffler.BookQueryResult
import com.yana.shuffler.contracts.HomeContract
import com.yana.shuffler.models.room.BookDao
import com.yana.shuffler.models.room.DateDao

class HomeModel(private val bookDao: BookDao, private val dateDao: DateDao): HomeContract.Model {
    private val uid = Firebase.auth.currentUser!!.uid
    override fun getBookDataByDate(
        dateToday: String,
        homeListener: HomeContract.Model.HomeListener
    ) {
        val checkBookStatus = dateDao.getBookStatus(false, uid)
        val checkBookDateTable = dateDao.checkIfTableExists(uid)
        val tableExist = bookDao.checkIfTableExists(uid)
        val bookData = bookDao.getTodayBook(dateToday, uid)



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

    override fun getFiveBooks(homeListener: HomeContract.Model.HomeListener) {
        val data = bookDao.getFiveBooks(uid)
        homeListener.fiveBooksResult(data)
    }

    override fun deleteShelf() {
        bookDao.deleteAllBooks(uid)
        dateDao.deleteAllDates(uid)
    }
}