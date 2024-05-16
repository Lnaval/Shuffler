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
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yana.shuffler.MainActivity
import com.yana.shuffler.R
import com.yana.shuffler.ShufflerApp
import com.yana.shuffler.contracts.AddedBook
import com.yana.shuffler.databinding.DialogConfirmDeleteBinding
import com.yana.shuffler.databinding.FragmentAddedBooksBinding
import com.yana.shuffler.models.room.RoomBook
import com.yana.shuffler.presenters.AddedBookPresenter

class AddedBooksFragment : Fragment(), AddedBook.View {
    private var _binding: FragmentAddedBooksBinding? = null
    private val binding get() = _binding!!

    private lateinit var addedBookPresenter: AddedBook.Presenter
    private lateinit var uid: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddedBooksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        uid = Firebase.auth.currentUser!!.uid
        Log.e("TAG", "onViewCreated: $uid", )
        addedBookPresenter = AddedBookPresenter(this, ShufflerApp.appContainer.addedBookModel)
        addedBookPresenter.onViewReady(uid)

        binding.deleteBookshelf.setOnClickListener {
            addedBookPresenter.onDeleteAllClicked(uid)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun setUpAddedBooksAdapter(books: List<RoomBook>) {
        if(books.isEmpty()){
            binding.emptyBookDialogue.visibility = View.VISIBLE
        } else {
            val addedBooksAdapter = AddedBooksAdapter{
                addedBookPresenter.onDeleteClicked(it, uid)
            }
            addedBooksAdapter.asyncListDiffer.submitList(books)
            binding.addedBooks.apply {
                adapter = addedBooksAdapter
                layoutManager = GridLayoutManager(requireContext(), 3)
            }
        }
    }

    override fun confirmToDeleteBookDisplay(id: Int) {
        val toDeleteBookDialog = Dialog(requireContext())
        val toDeleteBooksBinding = DialogConfirmDeleteBinding.inflate(layoutInflater, null, false)
        toDeleteBookDialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            setContentView(toDeleteBooksBinding.root)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            show()
        }

        toDeleteBooksBinding.message.text = getString(R.string.delete_book)

        toDeleteBooksBinding.confirm.setOnClickListener {
            addedBookPresenter.onConfirmDeletionClicked(id)
            toDeleteBookDialog.cancel()
            (activity as MainActivity).replaceFragment(AddedBooksFragment(), R.id.navBookList)
        }

        toDeleteBooksBinding.cancel.setOnClickListener {
            toDeleteBookDialog.cancel()
        }
    }

    override fun displayDeleteResult(result: String) {
        Toast.makeText(requireContext(), result, Toast.LENGTH_SHORT).show()
    }

    override fun confirmToDeleteAllBooks() {
        val toDeleteBookDialog = Dialog(requireContext())
        val toDeleteBooksBinding = DialogConfirmDeleteBinding.inflate(layoutInflater, null, false)
        toDeleteBookDialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            setContentView(toDeleteBooksBinding.root)
            window?.setLayout(900, 500)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            show()
        }

        toDeleteBooksBinding.message.text = getString(R.string.delete_bookshelf)

        toDeleteBooksBinding.confirm.setOnClickListener {
            addedBookPresenter.onConfirmDeleteAllClicked(uid)
            toDeleteBookDialog.cancel()
            (activity as MainActivity).replaceFragment(AddedBooksFragment(), R.id.navBookList)
        }

        toDeleteBooksBinding.cancel.setOnClickListener {
            toDeleteBookDialog.cancel()
        }
    }
}