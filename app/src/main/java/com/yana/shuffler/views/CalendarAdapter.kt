package com.yana.shuffler.views

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.yana.shuffler.databinding.LayoutCalendarItemBinding
import com.yana.shuffler.models.room.RoomDate

class CalendarAdapter(val onClickDate: ((id: Int) -> Unit)) : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {
    private val diffCallback = object : DiffUtil.ItemCallback<RoomDate>(){
        override fun areItemsTheSame(oldItem: RoomDate, newItem: RoomDate): Boolean {
            return oldItem.dateId == newItem.dateId
        }

        override fun areContentsTheSame(oldItem: RoomDate, newItem: RoomDate): Boolean {
            return oldItem == newItem
        }
    }
    class CalendarViewHolder(binding: LayoutCalendarItemBinding): RecyclerView.ViewHolder(binding.root) {
        val date = binding.dateText
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        return CalendarViewHolder(LayoutCalendarItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun getItemCount(): Int {
        return asyncListDiffer.currentList.size
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val item = asyncListDiffer.currentList[position]

        if(item.book!=-1){
            holder.date.setOnClickListener {
                onClickDate.invoke(item.dateId)
            }
        } else {
            holder.date.setTextColor(Color.BLACK)
            holder.date.setBackgroundColor(Color.WHITE)
        }
        holder.date.text = item.date
    }

    val asyncListDiffer = AsyncListDiffer(this, diffCallback)

}