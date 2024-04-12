package com.yana.shuffler.contracts

import android.content.Context
import com.yana.shuffler.models.room.RoomBook

interface ShowBookOnDateContract {
    interface Model{
        interface OnFinishLoadBookOnDateDataListener{
            fun loadedBookOnDateDate(data: RoomBook)
        }

        fun loadBookOnDateData(context: Context, id: Int, bookOnDateListener: OnFinishLoadBookOnDateDataListener)
    }

    interface View{
        fun displayLoadedData(data: RoomBook)
    }

    interface Presenter{
        fun requestBookOnDateData(context: Context, id: Int)
    }
}