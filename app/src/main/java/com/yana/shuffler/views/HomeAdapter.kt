package com.yana.shuffler.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yana.shuffler.R
import com.yana.shuffler.databinding.LayoutUserBooksHomeBinding
import com.yana.shuffler.models.room.RoomBook

class HomeAdapter: RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<RoomBook>(){
        override fun areItemsTheSame(oldItem: RoomBook, newItem: RoomBook): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RoomBook, newItem: RoomBook): Boolean {
            return oldItem==newItem
        }

    }

    val asyncListDiffer = AsyncListDiffer(this, diffCallback)
    class HomeViewHolder(binding: LayoutUserBooksHomeBinding) : RecyclerView.ViewHolder(binding.root){
        val title = binding.bookTitle
        val author = binding.bookAuthor
        val cover = binding.bookCover
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
       return HomeViewHolder(LayoutUserBooksHomeBinding.inflate(
           LayoutInflater.from(parent.context),
           parent,
           false
       ))
    }

    override fun getItemCount(): Int {
        return asyncListDiffer.currentList.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = asyncListDiffer.currentList[position]

        if(item!=null){
            holder.title.text = item.title
            holder.author.text = item.author

            val imageUrl = "https://covers.openlibrary.org/b/olid/${item.image}-M.jpg"
            Glide.with(holder.cover)
                .load(imageUrl)
                .centerCrop()
                .thumbnail(Glide.with(holder.cover).load(R.drawable.image_loading))
                .into(holder.cover)
        }
    }
}