package com.yana.shuffler.views

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso
import android.widget.SearchView
import androidx.core.view.isVisible
import com.yana.shuffler.R
import com.yana.shuffler.contracts.SearchContract
import com.yana.shuffler.databinding.DialogBottomSheetBookDetailsBinding
import com.yana.shuffler.databinding.DialogNoInternetBinding
import com.yana.shuffler.databinding.FragmentSearchBinding
import com.yana.shuffler.models.Book
import com.yana.shuffler.models.SearchModel
import com.yana.shuffler.presenters.SearchPresenter

class SearchFragment : Fragment(), SearchContract.View {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var searchPresenter: SearchPresenter
    private lateinit var searchedBooks: SearchedBooksAdapter
    private lateinit var searchKey: String
    private lateinit var mBottomSheetDialog: BottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchPresenter = SearchPresenter(this, SearchModel())

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
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun setUpSearchedItemsList(books: ArrayList<Book>) {
        searchedBooks = SearchedBooksAdapter{
            //open dialog sheet to show more book details
            //dialog sheet must contain an add to list button to add the book to the database
            setUpDialogBottomSheetBookDetails(it)
        }
        searchedBooks.saveData(books)
        binding.bookSearchResults.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = searchedBooks
        }
        binding.progressIndicator.isVisible = false
    }

    override fun notifyAddedBookResult() {
        //dismiss bottom sheet dialog when book is added
        //[have something to handle when the book isn't successfully added]
        mBottomSheetDialog.dismiss()

        Toast.makeText(requireContext(), "the book has been added", Toast.LENGTH_SHORT).show()
    }

    override fun addMoreBookResults(books: ArrayList<Book>) {
        Log.e("TAG", "added more books")
        searchedBooks.saveData(books)
        val positionStart = searchedBooks.itemCount - books.size
        Log.e("TAG", "Fragment: $positionStart | ${books.size}")
        searchedBooks.notifyItemRangeInserted(positionStart, books.size)
    }

    override fun showNoInternetDialog() {
        val mNoInternetDialog = Dialog(requireContext())
        val mNoInternetDialogBinding = DialogNoInternetBinding.inflate(layoutInflater, null, false)

        mNoInternetDialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            //setCancelable(false)
            setContentView(mNoInternetDialogBinding.root)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            show()
        }
    }

    private fun setUpDialogBottomSheetBookDetails(book: Book){
        mBottomSheetDialog = BottomSheetDialog(requireContext())
        val mBottomSheetBinding = DialogBottomSheetBookDetailsBinding.inflate(layoutInflater, null, false)

        mBottomSheetDialog.setContentView(mBottomSheetBinding.root)
        mBottomSheetBinding.apply {
            bookTitle.text = book.title
            bookAuthor.text = if(book.author.isNullOrEmpty()) "Not Found" else book.author.toString()
            bookPublished.text = if(book.firstPublishYear==null || book.firstPublishYear==0) "Not Found" else book.firstPublishYear.toString()
            bookSubject.text = if(book.subject.isNullOrEmpty()) "Not Found" else book.subject.toString()

            val imageUrl = "https://covers.openlibrary.org/b/olid/${book.image}-M.jpg"

            Picasso.get()
                .load(imageUrl)
                .resize(150, 200)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(bookImage)

            buttonAddToList.setOnClickListener{
                searchPresenter.addBook(book, requireContext())
            }
        }
        mBottomSheetDialog.show()
    }
}