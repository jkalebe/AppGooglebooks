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
import br.com.example.googlebooks.ui.BookDetailActivity
import br.com.example.googlebooks.ui.adapter.BookListAdapter
import br.com.example.googlebooks.ui.viewmodel.BookListViewModel
import kotlinx.android.synthetic.main.fragment_book_list.*

class BookListFragment:Fragment(){

    val viewModel: BookListViewModel by lazy {
        ViewModelProvider(this).get(BookListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_book_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewModel.state.observe(viewLifecycleOwner, Observer { state ->
            when(state){
                is BookListViewModel.State.Loading -> {
                    vwLoading.visibility = View.VISIBLE
                }
                is BookListViewModel.State.Loaded -> {
                    vwLoading.visibility = View.GONE
                    recyclerView.adapter = BookListAdapter(state.items, this::openBookDetail)
                }
                is BookListViewModel.State.Error ->{
                    vwLoading.visibility = View.GONE
                    if (!state.hasConsumed){
                        state.hasConsumed = true
                        Toast.makeText(requireContext(), R.string.error_loading, Toast.LENGTH_LONG).show()
                    }

                }
            }

        })
        viewModel.loadBooks()
    }

    private fun openBookDetail(volume: Volume){
        BookDetailActivity.open(requireContext(), volume)
    }
}