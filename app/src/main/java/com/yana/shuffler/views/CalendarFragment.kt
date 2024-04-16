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
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.yana.shuffler.MainActivity
import com.yana.shuffler.R
import com.yana.shuffler.contracts.CalendarContract
import com.yana.shuffler.databinding.DialogShuffleBinding
import com.yana.shuffler.databinding.FragmentCalendarBinding
import com.yana.shuffler.models.CalendarModel
import com.yana.shuffler.models.room.RoomDate
import com.yana.shuffler.presenters.CalendarPresenter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CalendarFragment : Fragment(), CalendarContract.View {
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    private lateinit var calendarPresenter: CalendarPresenter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calendarPresenter = CalendarPresenter(this, CalendarModel())
        calendarPresenter.requestDateTableData(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun setUpShuffleDialog(result: Boolean) {
        if(!result) {
            val shuffleBooksDialog = Dialog(requireContext())
            val sBDialogBinding = DialogShuffleBinding.inflate(layoutInflater, null, false)
            shuffleBooksDialog.apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setCancelable(false)
                setContentView(sBDialogBinding.root)
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                show()
            }

            sBDialogBinding.confirm.setOnClickListener {
                val daysAfter = sBDialogBinding.daysAfterInput.text.toString().toInt()
                calendarPresenter.shuffleList(daysAfter, requireContext())
                shuffleBooksDialog.cancel()
                calendarPresenter.requestDateTableData(requireContext())
            }
        } else {
            //display calendar
            val calendar = Calendar.getInstance()
            var dayInMonth = calendar.getActualMaximum(Calendar.DATE)

            val monthTextFormat = SimpleDateFormat("MMMM", Locale.getDefault())
            binding.monthText.text = monthTextFormat.format(calendar.time)

            calendarPresenter.requestCalendarData(calendar.time, dayInMonth, requireContext())

            binding.prevKey.setOnClickListener {
                calendar.add(Calendar.MONTH, -1)
                binding.monthText.text = monthTextFormat.format(calendar.time)
                dayInMonth = calendar.getActualMaximum(Calendar.DATE)
                calendarPresenter.requestCalendarData(calendar.time, dayInMonth, requireContext())
            }

            binding.nextKey.setOnClickListener {
                calendar.add(Calendar.MONTH, 1)
                binding.monthText.text = monthTextFormat.format(calendar.time)
                dayInMonth = calendar.getActualMaximum(Calendar.DATE)
                calendarPresenter.requestCalendarData(calendar.time, dayInMonth, requireContext())
            }
        }
    }

    override fun setUpCalendarView(dataForAdapter: ArrayList<RoomDate>) {
        val calendar = Calendar.getInstance()
        val dateToday = SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(calendar.time)
        val calendarAdapter = CalendarAdapter { date, view ->
            calendarPresenter.checkIfBookCanBeOpened(view, dateToday, date, requireContext())
        }

        calendarAdapter.asyncListDiffer.submitList(dataForAdapter)

        binding.calendarView.apply {
            adapter = calendarAdapter
            layoutManager = GridLayoutManager(requireContext(), 7)
        }
    }

    override fun displayBookForTheDay(bookId: Int, view: View) {
        val fragment = ShowBookOnDateFragment.newInstance(bookId)
        (activity as MainActivity).replaceFragment(fragment, R.id.navCalendar)
    }

    override fun onCantBeOpened() {
        Toast.makeText(requireContext(), "Too early to open", Toast.LENGTH_SHORT).show()
    }
}