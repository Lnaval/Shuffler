package com.yana.shuffler.contracts

import android.content.Context
import com.yana.shuffler.models.room.RoomBook

interface AddedBook {
    interface Model{
        interface OnFinishedListener{
            fun gettingAllBooksListener(books : List<RoomBook>)
        }

        fun getAllBooks(context: Context, onFinishedListener: OnFinishedListener)
    }

    interface Presenter{
        fun getAllBooks(context: Context)
    }

    interface View{
        fun setUpAddedBooksAdapter(books: List<RoomBook>)
    }
}