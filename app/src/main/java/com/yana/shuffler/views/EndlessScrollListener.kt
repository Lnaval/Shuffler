package com.yana.shuffler.views

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class EndlessScrollListener : RecyclerView.OnScrollListener() {

    private var currentPage = 1

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val totalItemCount = layoutManager.itemCount
        val lastVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition()

        if(totalItemCount-1 == lastVisibleItemPosition){
            //reached last
            currentPage++
            onLoadMore(currentPage)
            //Log.e("TAG", "${layoutManager.itemCount}")
        }
    }

    abstract fun onLoadMore(page: Int)
}