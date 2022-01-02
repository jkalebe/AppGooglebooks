package br.com.example.googlebooks.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.example.googlebooks.model.BookHttp
import br.com.example.googlebooks.model.Volume
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookListViewModel: ViewModel(){


    private val _bookList = MutableLiveData<List<Volume>>()
    val booksList: LiveData<List<Volume>>
        get() = _bookList

    fun loadBooks(){
        if (_bookList.value != null) return

        viewModelScope.launch {
            val result = withContext(Dispatchers.IO){
                BookHttp.searchBook("Dominando o Android")
            }
            _bookList.value = result?.items!!
        }
    }
}