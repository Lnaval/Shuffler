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
import java.util.Locale

class CalendarModel: CalendarContract.Model {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun getCalendarData(month: String, dayInMonth: Int, context: Context, calendarListener: OnFinishCalendarListener) {
        val dayOfMonthStartAt = LocalDate.of(LocalDate.now().year, month.toInt(), 1).dayOfWeek.value

        val dateTableList = AddedBookDatabase.getInstance(context).dateDao().getAll()

        val dateListTableForEachMonth = dateTableList.filter { listItem ->
            listItem.date.substring(0,2)==month
        }

        val dataForAdapter = getDataForCalendarAdapter(dateListTableForEachMonth, dayOfMonthStartAt, dayInMonth)
        calendarListener.finishedGettingCalendarData(dataForAdapter)
    }

    override fun getDateTableData(context: Context, calendarListener: OnFinishCalendarListener) {
        val result = AddedBookDatabase.getInstance(context).dateDao().getAll()
        calendarListener.finishedGettingDateTableData(result.isNotEmpty())
    }

    //add listener function?
    override fun shuffleRetrievedData(daysAfter: Int, context: Context, calendarListener: OnFinishCalendarListener) {
        val currentBookList = AddedBookDatabase.getInstance(context).bookDao().getAllBookInList()
        val shuffleList = currentBookList.shuffled()
        assignBookToDate(daysAfter, shuffleList, context)
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
                list.add(RoomDate(a, "", -1,false))
            }
        }

        for(item in dateList){
            val dateOfItem = item.date.substring(3,5).toInt()
            for(i in (list.size-dayOfMonthStartAt+1)..<dateOfItem){
                list.add(RoomDate(i, i.toString(), -1,false))
            }
            list.add(RoomDate(item.id, dateOfItem.toString(), item.book, true))
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