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
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yana.shuffler.MainActivity
import com.yana.shuffler.R
import com.yana.shuffler.ShufflerApp
import com.yana.shuffler.contracts.ShowBookOnDateContract
import com.yana.shuffler.databinding.DialogCompletedBinding
import com.yana.shuffler.databinding.FragmentShowBookOnDateBinding
import com.yana.shuffler.models.room.RoomBook
import com.yana.shuffler.presenters.BookOnDatePresenter

class ShowBookOnDateFragment : Fragment(), ShowBookOnDateContract.View {
    private var _binding: FragmentShowBookOnDateBinding? = null
    private val binding get() = _binding!!
    private lateinit var bookOnDatePresenter: BookOnDatePresenter
    private lateinit var uid: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShowBookOnDateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = requireArguments().getInt(ID_KEY)
        uid = Firebase.auth.currentUser!!.uid
        bookOnDatePresenter = BookOnDatePresenter(this, ShufflerApp.appContainer.bookOnDateModel)
        bookOnDatePresenter.onClickDateOnCalendar(id, uid)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ID_KEY = "id_key"
        fun newInstance(id: Int) : ShowBookOnDateFragment {
            val fragment = ShowBookOnDateFragment()
            val bundle = Bundle()

            bundle.putInt(ID_KEY, id)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun displayLoadedData(data: RoomBook, bookStatus: Boolean) {
        val imageUrl = "https://covers.openlibrary.org/b/olid/${data.image}-L.jpg"
        Log.e("TAG", "DATA: $data")

        binding.apply {
            Glide.with(bookCover)
                .load(imageUrl)
                .centerCrop()
                .thumbnail(Glide.with(bookCover).load(R.drawable.image_loading))
                .into(bookCover)

            bookTitle.text = data.title
            bookAuthor.text = data.author
            subjectDescription.text = data.subjectDesc

            if(bookStatus){
                binding.checkMark.setBackgroundResource(R.drawable.checked_icon)
            }

            binding.completedButton.setOnClickListener {
                bookOnDatePresenter.onClickUpdateStatus(data.id, uid)
            }
        }
    }

    override fun displayUpdatedBookStatus(result: Boolean) {
        if(result){
            binding.checkMark.setBackgroundResource(R.drawable.checked_icon)
        } else {
            binding.checkMark.setBackgroundResource(R.drawable.unchecked_icon)
        }
    }

    override fun displayFinishedShelf(message: String) {
        val completedDialog = Dialog(requireContext())
        val cSDialogCompletedBinding = DialogCompletedBinding.inflate(layoutInflater, null, false)
        completedDialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            setContentView(cSDialogCompletedBinding.root)
            window?.setLayout(950, 600)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            show()
        }
        cSDialogCompletedBinding.confirm.setOnClickListener{
            bookOnDatePresenter.onClickDeleteShelf(uid)
            completedDialog.cancel()
            (activity as MainActivity).replaceFragment(HomeFragment(), R.id.navHome)
        }

        cSDialogCompletedBinding.cancel.setOnClickListener{
            completedDialog.cancel()
        }
    }
}