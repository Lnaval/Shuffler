package com.yana.shuffler.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.yana.shuffler.MainActivity
import com.yana.shuffler.R
import com.yana.shuffler.databinding.FragmentHomeBinding
import com.yana.shuffler.models.room.AddedBookDatabase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dateToday = SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(Date())
        val bookData = AddedBookDatabase.getInstance(requireContext()).bookDao().getTodayBook(dateToday)
//        val bookToday = AddedBookDatabase.getInstance(requireContext()).dateDao().getTodayBook(dateToday)
//
//        val bookData = AddedBookDatabase.getInstance(requireContext()).bookDao().getBook(bookToday.book)
        binding.bookTitle.text = bookData.title
        val url = "https://covers.openlibrary.org/b/olid/${bookData.image}-M.jpg"
//        Picasso.get()
//            .load(url)
//            .placeholder(R.drawable.ic_launcher_foreground)
//            .resize(130, 200)
//            .centerCrop()
//            .error(R.drawable.ic_launcher_foreground)
//            .into(binding.bookCover)

        Glide.with(this)
            .load(url)
            .centerCrop()
            .thumbnail(Glide.with(this).load(R.drawable.image_loading))
            .into(binding.bookCover)

        binding.bookCover.setOnClickListener {
            val fragment = ShowBookOnDateFragment.newInstance(bookData.id)
            (activity as MainActivity).replaceFragment(fragment, R.id.navHome)
        }

        binding.userBooksSeeAll.setOnClickListener {
            (activity as MainActivity).replaceFragment(AddedBooksFragment(), R.id.navBookList)
        }

        val data = AddedBookDatabase.getInstance(requireContext()).bookDao().getFiveBooks()
        Log.e("TAG", "$data")
        val homeAdapter = HomeAdapter()
        homeAdapter.asyncListDiffer.submitList(data)
        binding.homeUserBookList.apply {
            adapter = homeAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}