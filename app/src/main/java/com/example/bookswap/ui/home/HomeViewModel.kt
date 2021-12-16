package com.example.bookswap.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookswap.model.Book
import com.example.bookswap.model.Post
import com.example.bookswap.network.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {

    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData <List<Post>> = _posts

    val accessToken = MutableLiveData<String>("")

    val statusCode = MutableLiveData<Int>()

    fun getFeed(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                try{
                    val response = Api.retrofitService.getFeed(accessToken.value!!)
                    _posts.postValue(response)
                    Log.d("homeviewmodel", response.toString())
                }catch(e: Exception){
                    Log.d("homeviewmodel", e.localizedMessage)
                }

            }
        }
    }
}