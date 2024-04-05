package com.yana.shuffler.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.yana.shuffler.R
import com.yana.shuffler.databinding.LayoutUserAddedBooksBinding
import com.yana.shuffler.models.room.RoomBook

class AddedBooksAdapter : RecyclerView.Adapter<AddedBooksAdapter.AddedViewHolder>() {
    private val diffCallback = object : DiffUtil.ItemCallback<RoomBook>(){
        override fun areItemsTheSame(oldItem: RoomBook, newItem: RoomBook): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RoomBook, newItem: RoomBook): Boolean {
            return  oldItem == newItem
        }

    }
    val asyncListDiffer = AsyncListDiffer(this, diffCallback)
    class AddedViewHolder(binding: LayoutUserAddedBooksBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.bookTitle
        val author = binding.bookAuthor
        val image = binding.bookImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddedViewHolder {
        return AddedViewHolder(
            LayoutUserAddedBooksBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun getItemCount(): Int {
        return asyncListDiffer.currentList.size
    }

    override fun onBindViewHolder(holder: AddedViewHolder, position: Int) {
        val item = asyncListDiffer.currentList[position]

        holder.title.text = item.title
        holder.author.text = item.author
        val imageUrl = "https://covers.openlibrary.org/b/olid/${item.image}-M.jpg"
        Picasso.get()
            .load(imageUrl)
            .resize(100, 150)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.image)
    }

}
