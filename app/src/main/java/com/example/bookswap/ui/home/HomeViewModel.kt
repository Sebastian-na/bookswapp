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
                    Log.d("homeviewmodel", "from home")
                    Log.d("homeviewmodel", accessToken.value.toString())
                    val response = Api.retrofitService.getFeed(accessToken=accessToken.value!!)
                    Log.d("homeviewmodel", response.toString())
                    _posts.postValue(response)
                }catch(e: Exception){
                    Log.d("homeviewmodel", e.toString())
                }

            }
        }
    }
}