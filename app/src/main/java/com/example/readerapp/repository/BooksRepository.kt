package com.example.readerapp.repository

import com.example.readerapp.data.Resources
import com.example.readerapp.model.Item
import com.example.readerapp.network.BooksApi
import javax.inject.Inject

class BooksRepository @Inject constructor(private val api : BooksApi){

    suspend fun getBooks(searchQuery: String):Resources<List<Item>>{
        return try {
            Resources.Loading(data = "Loading...")
            val itemList = api.getBooks(searchQuery).items
            if (itemList.isNotEmpty()) Resources.Loading(data = false)
            Resources.Success(data = itemList)
        }catch (exception:Exception){
            Resources.Error(message = exception.message.toString())

        }
    }

    suspend fun getBookInfo(bookId : String): Resources<Item> {
        val response = try {
            Resources.Loading(data = true)
            api.getBookInfo(bookId)
        }catch (exception:Exception){
            return Resources.Error(
                message = "An error occurred ${exception.message.toString()}"
            )
        }
        Resources.Loading(data = false)
        return Resources.Success(data = response)
    }
}