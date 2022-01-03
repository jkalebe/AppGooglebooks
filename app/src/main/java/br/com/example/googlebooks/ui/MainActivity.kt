package br.com.example.googlebooks.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.example.googlebooks.R
import br.com.example.googlebooks.model.BookHttp
import br.com.example.googlebooks.model.Volume
import br.com.example.googlebooks.ui.adapter.BookListAdapter
import br.com.example.googlebooks.ui.viewmodel.BookListViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    val viewModel: BookListViewModel by lazy {
        ViewModelProvider(this).get(BookListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        viewModel.state.observe(this, Observer { state ->
            when(state){
                is BookListViewModel.State.Loading -> {
                    vwLoading.visibility = View.VISIBLE
                }
                is BookListViewModel.State.Loaded -> {
                    vwLoading.visibility = View.GONE
                    recyclerView.adapter = BookListAdapter(state.items, this@MainActivity::openBookDetail)
                }
                is BookListViewModel.State.Error ->{
                    vwLoading.visibility = View.GONE
                    if (!state.hasConsumed){
                        state.hasConsumed = true
                        Toast.makeText(this@MainActivity, R.string.error_loading, Toast.LENGTH_LONG).show()
                    }

                }
            }

        })
        viewModel.loadBooks()

    }

    private fun openBookDetail(volume: Volume){
        BookDetailActivity.open(this, volume)
    }
}