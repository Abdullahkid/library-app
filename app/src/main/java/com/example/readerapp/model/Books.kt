package com.example.readerapp.model

data class Books(
    val items: List<Item>,
    val kind: String,
    val totalItems: Int
)