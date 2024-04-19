package com.yana.shuffler.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yana.shuffler.R
import com.yana.shuffler.databinding.LayoutUserAddedBooksBinding
import com.yana.shuffler.models.room.RoomBook

class AddedBooksAdapter(private val onLongClickDeleteItem: ((Int) -> Unit)) : RecyclerView.Adapter<AddedBooksAdapter.AddedViewHolder>() {
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

        Glide.with(holder.image)
            .load(imageUrl)
            .centerCrop()
            .thumbnail(Glide.with(holder.image).load(R.drawable.image_loading))
            .into(holder.image)

        holder.image.setOnLongClickListener {
            onLongClickDeleteItem.invoke(item.id)
            return@setOnLongClickListener true
        }
    }
}
