package com.example.bookswap.model

data class PostingBook(
                       val owner: String?,
                       val title: String,
                       val author: String,
                       val genre: String,
                       val tags: List<String>,
                       val description: String,
)
