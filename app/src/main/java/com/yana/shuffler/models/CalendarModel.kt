package com.yana.shuffler.models

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yana.shuffler.contracts.CalendarContract
import com.yana.shuffler.contracts.CalendarContract.Model.OnFinishCalendarListener
import com.yana.shuffler.models.room.BookDao
import com.yana.shuffler.models.room.DateDao
import com.yana.shuffler.models.room.RoomBook
import com.yana.shuffler.models.room.RoomDate
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CalendarModel (private val bookDao: BookDao, private val dateDao: DateDao): CalendarContract.Model {
    private val uid = Firebase.auth.currentUser!!.uid
    @RequiresApi(Build.VERSION_CODES.O)
    override fun getCalendarData(
        date: Date,
        dayInMonth: Int,
        calendarListener: OnFinishCalendarListener
    ) {
        val monthNumberFormat = SimpleDateFormat("MM-yyyy", Locale.getDefault())
        val year = monthNumberFormat.format(date).substring(3,7)
        val month = monthNumberFormat.format(date).substring(0,2)
        val dayOfMonthStartAt = LocalDate.of(year.toInt(), month.toInt(), 1).dayOfWeek.value

        val dateTableList = dateDao.getAll(uid)

        val dateListTableForEachMonth = dateTableList.filter { listItem ->
            val checkDate = listItem.date.substring(0,3)+listItem.date.substring(6,10)
            checkDate == "$month-$year"
        }

        val dataForAdapter = getDataForCalendarAdapter(dateListTableForEachMonth, dayOfMonthStartAt, dayInMonth)
        calendarListener.finishedGettingCalendarData(dataForAdapter)
    }

    override fun getDateTableData(calendarListener: OnFinishCalendarListener) {
        val check = bookDao.getAllBookInList(uid)
        if(check.isEmpty()){
            calendarListener.checkBookListSize()
        } else {
            val result = dateDao.getAll(uid)
            calendarListener.finishedGettingDateTableData(result.isNotEmpty())
        }
    }

    override fun shuffleRetrievedData(
        daysAfter: String,
        calendarListener: OnFinishCalendarListener
    ) {
        val currentBookList = bookDao.getAllBookInList(uid)
        if(daysAfter.isNotEmpty()){
            if (daysAfter.toInt() != 0) {
                val shuffleList = currentBookList.shuffled()
                assignBookToDate(daysAfter.toInt(), shuffleList)
            } else {
                calendarListener.errorMessage("Invalid input")
            }
        } else {
            calendarListener.errorMessage("Invalid input")

        }
    }

    override fun checkIfBookCanBeOpened(
        dateToday: String,
        bookDateId: Int,
        calendarListener: OnFinishCalendarListener
    ) {
        val item = dateDao.getIndividual(bookDateId, uid)

        if(item.date <= dateToday){
            calendarListener.canBookOnDateBeOpen(item.book)
        } else {
            calendarListener.errorMessage("Too early to open")
        }
    }

    private fun assignBookToDate(daysAfter: Int, shuffledList: List<RoomBook>) {
        var count = 0
        val newDateList = ArrayList<RoomDate>()
        for(item in shuffledList){
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_YEAR, count)
            val dateFormat = SimpleDateFormat("MM-dd-yyyy", Locale.getDefault())
            val dayToAssign = dateFormat.format(calendar.time)

            newDateList.add(RoomDate(0,dayToAssign.toString(), item.id, false, uid))
            count += daysAfter
        }
        dateDao.addAll(newDateList)
    }

    private fun getDataForCalendarAdapter(dateList: List<RoomDate>, dayOfMonthStartAt: Int, dayInMonth: Int) : ArrayList<RoomDate>{
        val list = ArrayList<RoomDate>()

        if(dayOfMonthStartAt!=7){
            for(a in 1..dayOfMonthStartAt){
                list.add(RoomDate(0, "", -1,false, "0"))
            }
        }

        for(item in dateList){
            val dateOfItem = item.date.substring(3,5).toInt()
            for(i in (list.size-dayOfMonthStartAt+1)..<dateOfItem){
                list.add(RoomDate(0, i.toString(), -1,false, "0"))
            }
            list.add(RoomDate(item.dateId, dateOfItem.toString(), item.book, false, uid))
        }

        if(list.size <= dayInMonth){
            val listSize: Int = if(dayOfMonthStartAt==7){
                list.size
            } else {
                list.size-dayOfMonthStartAt
            }
            for(i in listSize+1..dayInMonth){
                list.add(RoomDate(0, i.toString(), -1,false, "0"))
            }
        }
        //Log.e("counting", "$list")
        return list
    }
}