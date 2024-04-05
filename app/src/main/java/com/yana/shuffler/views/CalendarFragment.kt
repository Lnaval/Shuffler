package com.yana.shuffler.views

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.yana.shuffler.databinding.DialogNoInternetBinding
import com.yana.shuffler.databinding.FragmentCalendarBinding
import com.yana.shuffler.models.room.AddedBookDatabase
import com.yana.shuffler.models.room.RoomBook
import com.yana.shuffler.models.room.RoomDate
import java.util.Calendar

class CalendarFragment : Fragment() {
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //do something
        checkBookDateAssignment(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    private fun checkBookDateAssignment(result: Boolean){
        if(!result){
            val mNoInternetDialog = Dialog(requireContext())
            val mNoInternetDialogBinding = DialogNoInternetBinding.inflate(layoutInflater, null, false)

            mNoInternetDialog.apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                //setCancelable(false)
                setContentView(mNoInternetDialogBinding.root)
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                show()
            }

            val currentBookList = AddedBookDatabase.getInstance(requireContext()).bookDao().getAllBookInList()
            val shuffledList = currentBookList.shuffled()
            assignBookToDate(daysAfter = 5, shuffledList = shuffledList)
        } else {
            //proceed to display calendar since shuffled na siya
        }
    }

    private fun assignBookToDate(daysAfter: Int, shuffledList: List<RoomBook>) {
        var count = 0
        val dateList = ArrayList<RoomDate>()
        for(element in shuffledList){
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_YEAR, count)
            val dayToAssign = calendar.time

            dateList.add(RoomDate(0,dayToAssign.toString(), element.id))
            count += daysAfter
        }
        AddedBookDatabase.getInstance(requireContext()).dateDao().addAll(dateList)
    }
}