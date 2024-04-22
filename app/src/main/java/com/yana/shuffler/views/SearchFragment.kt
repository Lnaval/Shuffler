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
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.yana.shuffler.R
import com.yana.shuffler.ShufflerApp
import com.yana.shuffler.contracts.SearchContract
import com.yana.shuffler.databinding.DialogBottomSheetBookDetailsBinding
import com.yana.shuffler.databinding.DialogNoInternetBinding
import com.yana.shuffler.databinding.FragmentSearchBinding
import com.yana.shuffler.models.Book
import com.yana.shuffler.presenters.SearchPresenter

class SearchFragment : Fragment(), SearchContract.View {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var searchPresenter: SearchPresenter
    private lateinit var searchedBooks: SearchedBooksAdapter
    private lateinit var searchKey: String
    private lateinit var mBottomSheetDialog: BottomSheetDialog
    private lateinit var mBottomSheetBinding: DialogBottomSheetBookDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchPresenter = SearchPresenter(this, ShufflerApp.appContainer.searchModel)

        val search = binding.searchView
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query!=null) {
                    search.clearFocus()
                    searchKey = query
                    searchPresenter.searchBooks(searchKey, 1)
                    binding.progressIndicator.isVisible = true
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        binding.bookSearchResults.addOnScrollListener(object : EndlessScrollListener() {
            override fun onLoadMore(page: Int) {
                searchPresenter.searchMore(searchKey, page)
                binding.progressIndicator.isVisible = true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun setUpSearchedItemsList(books: ArrayList<Book>) {
        searchedBooks = SearchedBooksAdapter{
            searchPresenter.viewBook(it)
        }
        searchedBooks.saveData(books)
        binding.bookSearchResults.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = searchedBooks
        }
        binding.progressIndicator.isVisible = false
    }

    override fun notifyAddedBookResult(result: String) {
        mBottomSheetDialog.dismiss()
        Toast.makeText(requireContext(), result, Toast.LENGTH_SHORT).show()
    }

    override fun addMoreBookResults(books: ArrayList<Book>) {
        searchedBooks.saveData(books)
        val positionStart = searchedBooks.itemCount - books.size
        searchedBooks.notifyItemRangeInserted(positionStart, books.size)
        binding.progressIndicator.isVisible = false
    }

    override fun setUpDialogBottomSheetBookDetails(
        title: String,
        author: String,
        firstYearPublished: String,
        image: String?,
        subjects: String
    ) {
        mBottomSheetDialog = BottomSheetDialog(requireContext())
        mBottomSheetBinding = DialogBottomSheetBookDetailsBinding.inflate(layoutInflater, null, false)

        mBottomSheetDialog.setContentView(mBottomSheetBinding.root)
        mBottomSheetBinding.apply {
            bookTitle.text = title
            bookAuthor.text = author
            bookPublished.text = firstYearPublished
            bookSubject.text = subjects

            val url = "https://covers.openlibrary.org/b/olid/${image}-M.jpg"
            Glide.with(bookImage)
                .load(url)
                .centerCrop()
                .thumbnail(Glide.with(bookImage).load(R.drawable.image_loading))
                .into(bookImage)

            searchPresenter.checkShuffledList()

            buttonAddToList.setOnClickListener{
                searchPresenter.addBook(title, author, firstYearPublished, image, subjects)
            }
        }
        mBottomSheetDialog.show()
    }

    override fun displayWhenShuffledListExists() {
        mBottomSheetBinding.buttonAddToList.visibility = View.INVISIBLE
    }

    override fun displaySearchError() {
        binding.progressIndicator.isVisible = false

        val searchErrorDialog = Dialog(requireContext())
        val searchErrorBinding = DialogNoInternetBinding.inflate(layoutInflater, null, false)
        searchErrorDialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(searchErrorBinding.root)
            window?.setLayout(900, 370)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            show()
        }

        searchErrorBinding.okayButton.setOnClickListener {
            searchErrorDialog.cancel()
        }
    }
}