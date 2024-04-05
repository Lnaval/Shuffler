package com.yana.shuffler.presenters

import android.content.Context
import com.yana.shuffler.contracts.AddedBook
import com.yana.shuffler.models.room.RoomBook

class AddedBookPresenter(
    private var mainView: AddedBook.View?,
    private var model: AddedBook.Model
) : AddedBook.Presenter, AddedBook.Model.OnFinishedListener{
    override fun gettingAllBooksListener(books: List<RoomBook>) {
        mainView!!.setUpAddedBooksAdapter(books)
    }

    override fun getAllBooks(context: Context) {
        model.getAllBooks(context, this)
    }
}