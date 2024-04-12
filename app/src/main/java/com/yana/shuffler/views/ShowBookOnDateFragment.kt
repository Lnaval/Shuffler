package com.yana.shuffler.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.yana.shuffler.R
import com.yana.shuffler.contracts.ShowBookOnDateContract
import com.yana.shuffler.databinding.FragmentShowBookOnDateBinding
import com.yana.shuffler.models.BookOnDateModel
import com.yana.shuffler.models.room.RoomBook
import com.yana.shuffler.presenters.BookOnDatePresenter

class ShowBookOnDateFragment : Fragment(), ShowBookOnDateContract.View {
    private var _binding: FragmentShowBookOnDateBinding? = null
    private val binding get() = _binding!!
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
        val bookOnDatePresenter = BookOnDatePresenter(this, BookOnDateModel())
        bookOnDatePresenter.requestBookOnDateData(requireContext(), id)
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

    override fun displayLoadedData(data: RoomBook) {
        val imageUrl = "https://covers.openlibrary.org/b/olid/${data.image}-M.jpg"

        binding.apply {
            Picasso.get()
                .load(imageUrl)
                .resize(250, 400)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(bookCover)
            bookTitle.text = data.title
            bookAuthor.text = data.author
        }
    }
}