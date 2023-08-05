package com.example.readerapp.screens.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readerapp.data.Resources
import com.example.readerapp.model.Item
import com.example.readerapp.repository.BooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.Exception

@HiltViewModel
class BookViewModel @Inject constructor(private val repository: BooksRepository)
    :ViewModel(){
    var list : List<Item> by mutableStateOf(listOf())
    var isLoading:Boolean by mutableStateOf(true)

    init {
        loadBooks("android")
    }

    fun loadBooks(s: String) {
        searchBooks("android")
    }

    fun searchBooks(query: String){
        viewModelScope.launch(Dispatchers.Default){
            if (query.isEmpty()){
                return@launch
            }
            try {
                when(val response = repository.getBooks(query)){
                    is Resources.Success -> {
                        list = response.data!!
                        if (list.isNotEmpty())isLoading=false
                    }
                    is Resources.Error->{
                        Log.d("Network", "searchBooks: Failed getting books")
                    }
                    else -> {}
                }
            }catch (exception : Exception){
                isLoading = false
                Log.d("Network","searchBooks: ${exception.message.toString()}")
            }
        }
    }
}