package com.yana.shuffler.models

import com.yana.shuffler.contracts.SearchContract
import com.yana.shuffler.contracts.SearchContract.Model.OnFinishedSearchListener
import com.yana.shuffler.models.room.BookDao
import com.yana.shuffler.models.room.DateDao
import com.yana.shuffler.models.room.RoomBook
import com.yana.shuffler.network.OpenLibraryInterface
import com.yana.shuffler.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchModel (private val bookDao: BookDao, private val dateDao: DateDao) : SearchContract.Model {
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
                searchListener.searchError()
            }
        })
    }

    override fun addBookToList(
        title: String,
        author: String,
        firstYearPublished: String,
        image: String?,
        subjects: String,
        uid: String
    ): Boolean {
        val bookToAdd = RoomBook(0, title, image, author, subjects, uid)

        return if(bookDao.checkIfBookExists(bookToAdd.title, uid)){
            false
        } else {
            bookDao.addBook(bookToAdd)
            true
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
                searchListener.searchError()
            }
        })
    }

    override fun viewBook(book: Book, searchListener: OnFinishedSearchListener): Book {
        val subjects =
            if (book.subject.isNullOrEmpty()) {
                listOf("Not Found")
            } else {
                if (book.subject.size > 10) book.subject.take(10) else book.subject
            }
        return Book(book.title, book.image, book.author, book.firstPublishYear, subjects)
    }

    override fun doesShuffledListExist(
        searchListener: OnFinishedSearchListener,
        uid: String
    ): Boolean {
        return dateDao.checkIfTableExists(uid)
    }
}