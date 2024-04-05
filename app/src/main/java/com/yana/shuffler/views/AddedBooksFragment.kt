package com.yana.shuffler.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.yana.shuffler.contracts.AddedBook
import com.yana.shuffler.databinding.FragmentAddedBooksBinding
import com.yana.shuffler.models.AddedBookModel
import com.yana.shuffler.models.room.RoomBook
import com.yana.shuffler.presenters.AddedBookPresenter

class AddedBooksFragment : Fragment(), AddedBook.View {
    private var _binding: FragmentAddedBooksBinding? = null
    private val binding get() = _binding!!

    private lateinit var addedBookPresenter: AddedBook.Presenter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddedBooksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addedBookPresenter = AddedBookPresenter(this, AddedBookModel())
        addedBookPresenter.getAllBooks(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun setUpAddedBooksAdapter(books: List<RoomBook>) {
        val addedBooksAdapter = AddedBooksAdapter()
        addedBooksAdapter.asyncListDiffer.submitList(books)
        binding.addedBooks.apply {
            adapter = addedBooksAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}