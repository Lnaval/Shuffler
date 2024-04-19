package com.yana.shuffler.views

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.yana.shuffler.AuthActivity
import com.yana.shuffler.BookQueryResult
import com.yana.shuffler.MainActivity
import com.yana.shuffler.R
import com.yana.shuffler.contracts.HomeContract
import com.yana.shuffler.databinding.FragmentHomeBinding
import com.yana.shuffler.models.HomeModel
import com.yana.shuffler.models.room.AddedBookDatabase
import com.yana.shuffler.models.room.RoomBook
import com.yana.shuffler.presenters.HomePresenter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val SP_STRING = "sharedPrefs"
private const val AUTH_KEY = "name"
class HomeFragment : Fragment(), HomeContract.View {
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
        val homePresenter = HomePresenter(this, HomeModel())
        homePresenter.requestBookDataByDateToday(requireContext(), dateToday)
        homePresenter.requestFiveBooks(requireContext())

        val userEmail = requireContext().getSharedPreferences(SP_STRING, MODE_PRIVATE)
            .getString(AUTH_KEY, "")
        binding.subtitle.append(userEmail)

        binding.userBooksSeeAll.setOnClickListener {
            (activity as MainActivity).replaceFragment(AddedBooksFragment(), R.id.navBookList)
        }
        binding.logoutButton.setOnClickListener{
            val sharedPreferences = requireContext().getSharedPreferences(SP_STRING, MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString(AUTH_KEY, "false")
            editor.apply()

            val intent = Intent(this@HomeFragment.context, AuthActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun displayRetrievedBook(book: RoomBook) {
        binding.bookTitle.text = book.title
        val url = "https://covers.openlibrary.org/b/olid/${book.image}-M.jpg"

        Glide.with(this)
            .load(url)
            .centerCrop()
            .thumbnail(Glide.with(this).load(R.drawable.image_loading))
            .into(binding.bookCover)

        binding.bookCover.setOnClickListener {
            val fragment = ShowBookOnDateFragment.newInstance(book.id)
            (activity as MainActivity).replaceFragment(fragment, R.id.navCalendar)
        }
    }

    override fun displayRetrievedFiveBooks(books: List<RoomBook>) {
        val homeAdapter = HomeAdapter()
        homeAdapter.asyncListDiffer.submitList(books)
        binding.homeUserBookList.apply {
            adapter = homeAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun displayAlreadyReadMessage(message: BookQueryResult) {
        binding.messageTextCard.visibility = View.VISIBLE
        binding.messageText.text = message.identifier

        if(message == BookQueryResult.Completed){
            binding.deleteBookshelf.visibility = View.VISIBLE
            binding.deleteBookshelf.setOnClickListener{
                AddedBookDatabase.getInstance(requireContext()).bookDao().deleteAllBooks()
                AddedBookDatabase.getInstance(requireContext()).dateDao().deleteAllDates()
                (activity as MainActivity).replaceFragment(this@HomeFragment, R.id.navHome)
            }
        }
    }
}