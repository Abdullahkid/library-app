package com.example.readerapp.screens.details

import androidx.lifecycle.ViewModel
import com.example.readerapp.data.Resources
import com.example.readerapp.model.Item
import com.example.readerapp.repository.BooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: BooksRepository)
    :ViewModel(){
    suspend fun getBookInfo(bookId:String):Resources<Item>{
//        viewModelScope.launch {
//            item = repository.getBookInfo(bookId = bookId)
//        }
        return repository.getBookInfo(bookId)
    }
}