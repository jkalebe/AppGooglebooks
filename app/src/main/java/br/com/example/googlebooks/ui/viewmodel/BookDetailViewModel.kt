package br.com.example.googlebooks.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.example.googlebooks.model.Volume
import br.com.example.googlebooks.repository.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookDetailViewModel(
    private val repository: BookRepository
):ViewModel(){
    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    fun onCreate(volume: Volume) {
        viewModelScope.launch {
            _isFavorite.value = repository.isFavorite(volume.id)
        }
    }

    fun saveToFavorites(volume: Volume){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repository.save(volume)
            }
            _isFavorite.value = repository.isFavorite(volume.id)
        }
    }

    fun removeFromFavorites(volume: Volume){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repository.delete(volume)
            }
            _isFavorite.value = repository.isFavorite(volume.id)
        }
    }
}