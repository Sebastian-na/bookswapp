package com.example.bookswap.model

data class Book(
    val _id: String,
    val owner: String?,
    val title: String,
    val author: String,
    val genre: String,
    val tags: List<String>,
    val description: String,
    val photos: List<String>
    )