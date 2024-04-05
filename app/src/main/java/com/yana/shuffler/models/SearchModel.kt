package com.yana.shuffler.models

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.yana.shuffler.contracts.SearchContract
import com.yana.shuffler.models.room.AddedBookDatabase
import com.yana.shuffler.models.room.RoomBook
import com.yana.shuffler.network.OpenLibraryInterface
import com.yana.shuffler.network.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchModel : SearchContract.Model {
    private val apiService = RetrofitInstance.build().create(OpenLibraryInterface::class.java)

    override fun getBooks(searchKey: String, pageNumber: Int, searchListener: SearchContract.Model.OnFinishedSearchListener
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            val response = apiService.searchBooks(title = searchKey, page = pageNumber)
            response.let {
                searchListener.onFinishSearch(response.books)
            }
        }
    }

    override fun addBookToList(book: Book, context: Context, searchListener: SearchContract.Model.OnFinishedSearchListener) {
        //add book to room database
        val bookToAdd = RoomBook(0, book.title, book.image, book.author.toString())
        AddedBookDatabase.getInstance(context).bookDao().addBook(bookToAdd)
        searchListener.onBookAdded()
    }

    override fun getMoreBooks(searchKey: String, pageNumber: Int, searchListener: SearchContract.Model.OnFinishedSearchListener
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            val response = apiService.searchBooks(title = searchKey, page = pageNumber)
            response.let {
                Log.e("TAG", "${response.books}")
                searchListener.onFinishSearchMore(response.books)
            }
        }
    }

    override fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    return true
                }
            }
        }
        return false
    }
}