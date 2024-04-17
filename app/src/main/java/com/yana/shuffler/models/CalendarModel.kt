package com.yana.shuffler.models

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.yana.shuffler.contracts.CalendarContract
import com.yana.shuffler.contracts.CalendarContract.Model.OnFinishCalendarListener
import com.yana.shuffler.models.room.AddedBookDatabase
import com.yana.shuffler.models.room.RoomBook
import com.yana.shuffler.models.room.RoomDate
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CalendarModel: CalendarContract.Model {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun getCalendarData(date: Date, dayInMonth: Int, context: Context, calendarListener: OnFinishCalendarListener) {
        val monthNumberFormat = SimpleDateFormat("MM-yyyy", Locale.getDefault())
        val year = monthNumberFormat.format(date).substring(3,7)
        val month = monthNumberFormat.format(date).substring(0,2)
        val dayOfMonthStartAt = LocalDate.of(year.toInt(), month.toInt(), 1).dayOfWeek.value

        val dateTableList = AddedBookDatabase.getInstance(context).dateDao().getAll()

        val dateListTableForEachMonth = dateTableList.filter { listItem ->
            val checkDate = listItem.date.substring(0,3)+listItem.date.substring(6,10)
            checkDate == "$month-$year"
        }

        val dataForAdapter = getDataForCalendarAdapter(dateListTableForEachMonth, dayOfMonthStartAt, dayInMonth)
        calendarListener.finishedGettingCalendarData(dataForAdapter)
    }

    override fun getDateTableData(context: Context, calendarListener: OnFinishCalendarListener) {
        val check = AddedBookDatabase.getInstance(context).bookDao().getAllBookInList()
        if(check.isEmpty()){
            calendarListener.checkBookListSize()
        } else {
            val result = AddedBookDatabase.getInstance(context).dateDao().getAll()
            calendarListener.finishedGettingDateTableData(result.isNotEmpty())
        }
    }

    //add listener function?
    override fun shuffleRetrievedData(daysAfter: Int, context: Context, calendarListener: OnFinishCalendarListener) {
        val currentBookList = AddedBookDatabase.getInstance(context).bookDao().getAllBookInList()
        if (daysAfter != 0) {
            val shuffleList = currentBookList.shuffled()
            assignBookToDate(daysAfter, shuffleList, context)
        } else {
            calendarListener.errorMessage("Invalid input")
        }
    }

    override fun checkIfBookCanBeOpened(dateToday: String, bookDateId: Int, context: Context, calendarListener: OnFinishCalendarListener) {
        val item = AddedBookDatabase.getInstance(context).dateDao().getIndividual(bookDateId)

        if(item.date <= dateToday){
            calendarListener.canBookOnDateBeOpen(item.book)
        } else {
            calendarListener.errorMessage("Too early to open")
        }
    }

    private fun assignBookToDate(daysAfter: Int, shuffledList: List<RoomBook>, context: Context) {
        var count = 0
        val newDateList = ArrayList<RoomDate>()
        for(item in shuffledList){
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_YEAR, count)
            val dateFormat = SimpleDateFormat("MM-dd-yyyy", Locale.getDefault())
            val dayToAssign = dateFormat.format(calendar.time)

            newDateList.add(RoomDate(0,dayToAssign.toString(), item.id, false))
            count += daysAfter
        }
        AddedBookDatabase.getInstance(context).dateDao().addAll(newDateList)
    }

    private fun getDataForCalendarAdapter(dateList: List<RoomDate>, dayOfMonthStartAt: Int, dayInMonth: Int) : ArrayList<RoomDate>{
        val list = ArrayList<RoomDate>()

        if(dayOfMonthStartAt!=7){
            for(a in 1..dayOfMonthStartAt){
                list.add(RoomDate(0, "", -1,false))
            }
        }

        for(item in dateList){
            val dateOfItem = item.date.substring(3,5).toInt()
            for(i in (list.size-dayOfMonthStartAt+1)..<dateOfItem){
                list.add(RoomDate(0, i.toString(), -1,false))
            }
            list.add(RoomDate(item.dateId, dateOfItem.toString(), item.book, false))
        }

        if(list.size <= dayInMonth){
            val listSize: Int = if(dayOfMonthStartAt==7){
                list.size
            } else {
                list.size-dayOfMonthStartAt
            }
            for(i in listSize+1..dayInMonth){
                list.add(RoomDate(0, i.toString(), -1,false))
            }
        }
        Log.e("counting", "$list")
        return list
    }
}