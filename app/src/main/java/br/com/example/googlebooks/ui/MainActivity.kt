package br.com.example.googlebooks.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.example.googlebooks.R
import br.com.example.googlebooks.model.BookHttp
import br.com.example.googlebooks.ui.adapter.BookListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        lifecycleScope.launch {
            val result = withContext(Dispatchers.IO){
                BookHttp.searchBook("Dominando o Android")
            }
            result?.items?.let {
                recyclerView.adapter = BookListAdapter(it){ volume ->
                    Toast.makeText(this@MainActivity, volume.volumeInfo.title, Toast.LENGTH_LONG).show()
                } }
        }

    }
}