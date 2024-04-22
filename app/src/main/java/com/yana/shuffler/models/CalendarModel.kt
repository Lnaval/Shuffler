package com.yana.shuffler.models

import android.os.Build
import androidx.annotation.RequiresApi
import com.yana.shuffler.CalendarResult
import com.yana.shuffler.contracts.CalendarContract
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
    @RequiresApi(Build.VERSION_CODES.O)
    override fun getCalendarData(
        date: Date,
        dayInMonth: Int,
        uid: String
    ): ArrayList<RoomDate> {
        val monthNumberFormat = SimpleDateFormat("MM-yyyy", Locale.getDefault())
        val year = monthNumberFormat.format(date).substring(3, 7)
        val month = monthNumberFormat.format(date).substring(0, 2)
        val dayOfMonthStartAt = LocalDate.of(year.toInt(), month.toInt(), 1).dayOfWeek.value

        val dateTableList = dateDao.getAll(uid)

        val dateListTableForEachMonth = dateTableList.filter { listItem ->
            val checkDate = listItem.date.substring(0, 3) + listItem.date.substring(6, 10)
            checkDate == "$month-$year"
        }

        return getDataForCalendarAdapter(
            dateListTableForEachMonth,
            dayOfMonthStartAt,
            dayInMonth,
            uid
        )
    }

    override fun getDateTableData(uid: String): CalendarResult {
        val check = bookDao.getAllBookInList(uid)
        if(check.isEmpty()){
            return CalendarResult.EmptyBookShelf
        }
        val result = dateDao.getAll(uid)
        if(result.isEmpty()) {
            return CalendarResult.NotShuffled
        }
        return CalendarResult.ShuffledListExist
    }

    override fun shuffleRetrievedData(daysAfter: String, uid: String): String {
        val currentBookList = bookDao.getAllBookInList(uid)
        if(daysAfter.isNotEmpty()) {
            if (daysAfter.toInt() != 0) {
                val shuffleList = currentBookList.shuffled()
                assignBookToDate(daysAfter.toInt(), shuffleList, uid)
                return "Success"
            }
        }
        return "Invalid Input"
    }

    override fun checkIfBookCanBeOpened(dateToday: String, bookDateId: Int, uid: String): Int {
        val item = dateDao.getIndividual(bookDateId, uid)

        if(item.date <= dateToday) {
            return item.book
        }
        return -1
    }

    private fun assignBookToDate(daysAfter: Int, shuffledList: List<RoomBook>, uid: String) {
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

    private fun getDataForCalendarAdapter(dateList: List<RoomDate>, dayOfMonthStartAt: Int, dayInMonth: Int, uid: String) : ArrayList<RoomDate>{
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