package com.example.bookswap.model

data class Post(
    val _id: String,
    val owner: String,
    val ownerPhoto: String,
    val ownerName: String,
    val title: String,
    val author: String,
    val genre: String,
    val tags: List<String>,
    val description: String,
    val photos: List<String>,
    val wantedBooks: List<Book>
)
