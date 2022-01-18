package com.example.bookswap.model

data class UserData(
    val name: String,
    val email: String,
    val bio: String,
    val profilePic: String,
    val swaps: List<String>,
    val friends: List<String>,
)
