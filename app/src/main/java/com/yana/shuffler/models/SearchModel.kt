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

    override fun addBookToList(
        title: String,
        author: String,
        firstYearPublished: String,
        image: String?,
        subjects: String,
        context: Context,
        searchListener: OnFinishedSearchListener
    ) {
        val bookDao = AddedBookDatabase.getInstance(context).bookDao()
        //add book to room database
        val bookToAdd = RoomBook(0, title, image, author, subjects)

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

    override fun viewBook(book: Book, searchListener: OnFinishedSearchListener) {
        val author = if(book.author.isNullOrEmpty()) "Not Found" else book.author.toString()
        val year = if(book.firstPublishYear==0) "Not Found" else book.firstPublishYear.toString()

        val subjects =
            if(book.subject.isNullOrEmpty()){
                "Not Found"
            } else {
                if(book.subject.size > 10) book.subject.take(10).toString() else book.subject.toString()
            }
        searchListener.onViewBook(book.title, author, year, book.image, subjects)
    }

    override fun doesShuffledListExist(context: Context, searchListener: OnFinishedSearchListener) {
        val check = AddedBookDatabase.getInstance(context).dateDao().checkIfTableExists()

        if(check){
            searchListener.shuffledListExists()
        }
    }
}