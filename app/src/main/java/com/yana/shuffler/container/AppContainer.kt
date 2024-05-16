package com.yana.shuffler.container

import android.content.Context
import com.yana.shuffler.models.AddedBookModel
import com.yana.shuffler.models.BookOnDateModel
import com.yana.shuffler.models.CalendarModel
import com.yana.shuffler.models.HomeModel
import com.yana.shuffler.models.SearchModel
import com.yana.shuffler.models.room.AddedBookDatabase

class AppContainer(context: Context) {
    private val bookDao = AddedBookDatabase.getInstance(context).bookDao()
    private val dateDao = AddedBookDatabase.getInstance(context).dateDao()

    val addedBookModel = AddedBookModel(bookDao, dateDao)
    val bookOnDateModel = BookOnDateModel(bookDao, dateDao)
    val calendarModel = CalendarModel(bookDao, dateDao)
    val homeModel = HomeModel(bookDao, dateDao)
    val searchModel = SearchModel(bookDao, dateDao)
}