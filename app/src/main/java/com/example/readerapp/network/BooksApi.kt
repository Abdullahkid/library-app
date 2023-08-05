package com.example.readerapp.network

import com.example.readerapp.model.Books
import com.example.readerapp.model.Item
import com.example.readerapp.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface BooksApi {
    @GET("volumes")
    suspend fun getBooks(
        @Query("q") q :String,
        @Query("key") key : String = Constants.API_KEY
    ):Books

    @GET("volumes/{bookId}")
    suspend fun getBookInfo(@Path("bookId")bookId:String):Item

}