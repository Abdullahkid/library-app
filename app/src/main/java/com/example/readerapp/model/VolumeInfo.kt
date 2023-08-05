package com.example.readerapp.model

data class VolumeInfo(
    val authors: List<String>,
    val categories: List<String>,
    val description: String,
    val imageLinks: ImageLinks,
    val industryIdentifiers: List<IndustryIdentifier>,
    val language: String,
    val pageCount: Int,
    val previewLink: String,
    val publishedDate: String,
    val publisher: String,
    val title: String
)