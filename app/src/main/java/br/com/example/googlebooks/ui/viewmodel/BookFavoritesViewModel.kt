package br.com.example.googlebooks.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import br.com.example.googlebooks.repository.BookRepository

class BookFavoritesViewModel(
    repository: BookRepository
):ViewModel(){
    val favoriteBooks = repository.allFavorites().asLiveData()
}