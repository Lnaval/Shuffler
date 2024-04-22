package com.yana.shuffler.models

import com.yana.shuffler.BookQueryResult
import com.yana.shuffler.contracts.HomeContract
import com.yana.shuffler.models.room.BookDao
import com.yana.shuffler.models.room.DateDao
import com.yana.shuffler.models.room.RoomBook

class HomeModel(private val bookDao: BookDao, private val dateDao: DateDao): HomeContract.Model {
    override fun getBookDataByDate(
        dateToday: String,
        homeListener: HomeContract.Model.HomeListener, uid: String
    ) {
        val checkBookStatus = dateDao.getBookStatus(false, uid)
        val checkBookDateTable = dateDao.checkIfTableExists(uid)
        val tableExist = bookDao.checkIfTableExists(uid)
        val bookData = bookDao.getTodayBook(dateToday, uid)

        if(tableExist){
            if(!checkBookStatus && checkBookDateTable){
                homeListener.bookStatusResult(BookQueryResult.Completed)
            } else {
                if(bookData!=null){
                    homeListener.bookDataByDateResult(bookData)
                } else {
                    homeListener.bookStatusResult(BookQueryResult.OnTrack)
                }
            }
        } else {
            homeListener.bookStatusResult(BookQueryResult.Empty)
        }
    }

    override fun getFiveBooks(uid: String): List<RoomBook> {
        return bookDao.getFiveBooks(uid)
    }

    override fun deleteShelf(uid: String) {
        bookDao.deleteAllBooks(uid)
        dateDao.deleteAllDates(uid)
    }
}