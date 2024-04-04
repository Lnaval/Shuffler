package com.yana.shuffler.views

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.yana.shuffler.R
import com.yana.shuffler.databinding.LayoutSearchedItemsBinding
import com.yana.shuffler.models.Book

class SearchedBooksAdapter(private val onClickBook: ((Book) -> Unit)) : RecyclerView.Adapter<SearchedBooksAdapter.BooksViewHolder>() {
    private val diffCallback = object :DiffUtil.ItemCallback<Book>(){
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem == newItem
        }

    }

    private val asyncListDiffer = AsyncListDiffer(this, diffCallback)
    private var mainList = mutableListOf<Book>()

    fun saveData(list: List<Book>){
        mainList.addAll(list)
        asyncListDiffer.submitList(mainList)
        Log.e("TAG", "Adapter: ${mainList.size-5}")
        Log.e("TAG", "List: $mainList")
    }

    class BooksViewHolder(binding: LayoutSearchedItemsBinding) : RecyclerView.ViewHolder(binding.root){
        val title = binding.titleSearch
        val author = binding.authorSearch
        val image = binding.bookImage
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BooksViewHolder {
        return BooksViewHolder(LayoutSearchedItemsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
            )
        )
    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        val item = mainList[position]

        holder.title.text = item.title
        holder.author.text = if(item.author.isNullOrEmpty()) "Not Found" else item.author.toString()

        val imageUrl = "https://covers.openlibrary.org/b/olid/${item.image}-M.jpg"

        Picasso.get()
            .load(imageUrl)
            .resize(150, 200)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.image)

        holder.image.setOnClickListener {
            onClickBook.invoke(item)
        }
    }

    override fun getItemCount(): Int {
        return mainList.size
    }
}
