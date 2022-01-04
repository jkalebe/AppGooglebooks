package br.com.example.googlebooks.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.example.googlebooks.R
import br.com.example.googlebooks.model.Volume
import br.com.example.googlebooks.repository.BookRepository
import br.com.example.googlebooks.ui.viewmodel.BookDetailViewModel
import br.com.example.googlebooks.ui.viewmodel.BookFavoritesViewModel
import br.com.example.googlebooks.ui.viewmodel.BookVmFactory
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_book_detail.*

class BookDetailActivity : AppCompatActivity() {
    private val viewModel: BookDetailViewModel by lazy {
        ViewModelProvider(
            this,
            BookVmFactory(
                BookRepository(this)
            )
        ).get(BookDetailViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)
        val volume = intent.getParcelableExtra<Volume>(EXTRA_BOOK)
        if (volume != null){
            if(volume.volumeInfo.imageLinks?.smallThumbnail != null){
                Picasso.get().load(volume.volumeInfo.imageLinks?.smallThumbnail).into(
                    imgCover
                );
            }else {
                imgCover.setImageResource(R.drawable.ic_broken_image)
            }
            txtTitle.text = volume.volumeInfo.title
            txtAuthor.text = volume.volumeInfo.authors?.joinToString() ?: ""
            txtPages.text = volume.volumeInfo.pageCount?.toString() ?: ""
            txtDescription.text = volume.volumeInfo.description
            txtPublisher.text = volume.volumeInfo.publisher

            viewModel.isFavorite.observe(
                this,
                Observer { isFavorite ->
                    if (isFavorite){
                        fabFavorite.setImageResource(R.drawable.ic_delete)
                        fabFavorite.setOnClickListener{
                            viewModel.removeFromFavorites(volume)
                        }
                    }else {
                        fabFavorite.setImageResource(R.drawable.ic_add)
                        fabFavorite.setOnClickListener{
                            viewModel.saveToFavorites(volume)
                        }
                    }
                 }
            )
            viewModel.onCreate(volume)
        }else{
            finish()
        }
    }

    companion object{
        private const val EXTRA_BOOK = "book"

        fun open(context: Context, volume: Volume){
            val detailIntent = Intent(context, BookDetailActivity::class.java)
            detailIntent.putExtra("book", volume)
            context.startActivity(detailIntent)
        }
    }
}