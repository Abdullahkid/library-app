package com.example.readerapp.screens.home

import com.google.firebase.database.Exclude
import com.google.firebase.database.PropertyName


data class MBook(
    @Exclude var id : String? =null,
    var title : String? =null,
    var author : String? =null,
    @get:PropertyName("book_photo_url")
    @set:PropertyName("book_photo_url")
    var photoUrl:String? =null,
    var categories:String? =null,
    @get:PropertyName("publish_data")
    @set:PropertyName("publish_data")
    var publishDate:String? =null,
    var description:String? =null,
    @get:PropertyName("page_count")
    @set:PropertyName("page_count")
    var pageCount:String? =null,
    @get:PropertyName("start_reading")
    @set:PropertyName("start_reading")
    var startReading:com.google.firebase.Timestamp? =null,
    @get:PropertyName("finish_reading")
    @set:PropertyName("finish_reading")
    var finishReading:com.google.firebase.Timestamp? =null,
    var notes : String? =null,
    @get:PropertyName("user_id")
    @set:PropertyName("user_id")
    var userId : String? =null,
    var rating : Double? = null ,
    @get:PropertyName("google_book_id")
    @set:PropertyName("google_book_id")
    var googleBookId :String? = null ,
    var reviews : String? = null
)

