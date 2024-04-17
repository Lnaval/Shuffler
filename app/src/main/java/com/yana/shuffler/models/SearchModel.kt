package com.yana.shuffler.models

import android.content.Context
import android.util.Log
import com.yana.shuffler.contracts.SearchContract
import com.yana.shuffler.contracts.SearchContract.Model.OnFinishedSearchListener
import com.yana.shuffler.models.room.AddedBookDatabase
import com.yana.shuffler.models.room.RoomBook
import com.yana.shuffler.network.OpenLibraryInterface
import com.yana.shuffler.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchModel : SearchContract.Model {
    private val apiService = RetrofitInstance.build().create(OpenLibraryInterface::class.java)

    override fun getBooks(searchKey: String, pageNumber: Int, searchListener: OnFinishedSearchListener
    ) {
        val data = apiService.searchBooks(searchKey, pageNumber)
        data.enqueue(object : Callback<Books?> {
            override fun onResponse(call: Call<Books?>, response: Response<Books?>) {
                val result = response.body()
                if(result!=null){
                    searchListener.onFinishSearch(result.books)
                }
            }

            override fun onFailure(call: Call<Books?>, t: Throwable) {
                Log.e("TAG", "failed $t")
            }
        })
    }

    override fun addBookToList(book: Book, context: Context, searchListener: OnFinishedSearchListener) {
        val bookDao = AddedBookDatabase.getInstance(context).bookDao()
        //add book to room database
        val bookToAdd = RoomBook(0, book.title, book.image, book.author.toString())

        if(bookDao.checkIfBookExists(bookToAdd.title)){
            searchListener.onBookAdded("Book Already Added")
        } else {
            AddedBookDatabase.getInstance(context).bookDao().addBook(bookToAdd)
            searchListener.onBookAdded("Book Added")
        }
    }

    override fun getMoreBooks(searchKey: String, pageNumber: Int, searchListener: OnFinishedSearchListener
    ) {
        val data = apiService.searchBooks(searchKey, pageNumber)
        data.enqueue(object : Callback<Books?> {
            override fun onResponse(call: Call<Books?>, response: Response<Books?>) {
                val result = response.body()
                if(result!=null){
                    searchListener.onFinishSearchMore(result.books)
                }
            }

            override fun onFailure(call: Call<Books?>, t: Throwable) {
                Log.e("TAG", "failed $t")
            }
        })
    }
}