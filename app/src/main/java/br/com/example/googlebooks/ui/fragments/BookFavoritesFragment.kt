package br.com.example.googlebooks.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.example.googlebooks.R
import br.com.example.googlebooks.model.Volume
import br.com.example.googlebooks.repository.BookRepository
import br.com.example.googlebooks.ui.BookDetailActivity
import br.com.example.googlebooks.ui.adapter.BookListAdapter
import br.com.example.googlebooks.ui.viewmodel.BookFavoritesViewModel
import br.com.example.googlebooks.ui.viewmodel.BookListViewModel
import br.com.example.googlebooks.ui.viewmodel.BookVmFactory
import kotlinx.android.synthetic.main.fragment_book_list.*

class BookFavoritesFragment:Fragment(){

    private val viewModel: BookFavoritesViewModel by lazy {
        ViewModelProvider(
            this,
            BookVmFactory(
                BookRepository(requireContext())
            )
        ).get(BookFavoritesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_book_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewModel.favoriteBooks.observe(viewLifecycleOwner, Observer { items ->

                    vwLoading.visibility = View.GONE
                    recyclerView.adapter = BookListAdapter(items, this::openBookDetail)

        })
    }

    private fun openBookDetail(volume: Volume){
        BookDetailActivity.open(requireContext(), volume)
    }
}