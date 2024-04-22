package com.yana.shuffler.presenters

import com.yana.shuffler.models.enumclasses.CalendarResult
import com.yana.shuffler.contracts.CalendarContract
import java.util.Date

class CalendarPresenter(
    private var mainView: CalendarContract.View?,
    private val model: CalendarContract.Model
) : CalendarContract.Presenter{

    override fun onViewCalendarReady(date: Date, dayInMonth: Int, uid: String) {
        val data = model.getCalendarData(date, dayInMonth, uid)
        mainView!!.setUpCalendarView(data)
    }

    override fun checkCalendarData(uid: String) {
        when(model.getDateTableData(uid)){
           CalendarResult.EmptyBookShelf -> mainView!!.displayMessageWhenBookIsEmpty()
           CalendarResult.NotShuffled -> mainView!!.setUpShuffleDialog(false)
           CalendarResult.ShuffledListExist -> mainView!!.setUpShuffleDialog(true)
       }
    }

    override fun onClickDate(dateToday: String, bookDateId: Int, uid: String) {
        when(val item = model.checkIfBookCanBeOpened(dateToday, bookDateId, uid)){
            -1 -> mainView!!.resultDisplay("Too early to open")
            else -> mainView!!.displayBookForTheDay(item)
        }
    }

    override fun onConfirmShuffle(daysAfter: String, uid: String) {
        val message = model.shuffleRetrievedData(daysAfter, uid)
        mainView!!.resultDisplay(message)
    }
}