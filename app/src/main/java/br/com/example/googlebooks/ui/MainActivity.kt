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
import br.com.example.googlebooks.ui.adapter.BookPagerAdapter
import br.com.example.googlebooks.ui.viewmodel.BookListViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        viewPager.adapter = BookPagerAdapter(this)
        TabLayoutMediator(tabLayout, viewPager){ tab,position ->
            tab.setText(
                if(position == 0)
                    R.string.tabs_books
                else
                    R.string.tab_favorites
            )
        }.attach()


    }


}