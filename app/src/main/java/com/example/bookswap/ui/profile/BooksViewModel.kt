package com.example.bookswap.ui.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookswap.model.Book
import com.example.bookswap.network.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

const val TAG = "booksviewmodel"
class BooksViewModel: ViewModel() {
    val books = MutableLiveData<List<Book>>()

    val accessToken = MutableLiveData("")

    val type = MutableLiveData("")

    fun getAllBooks(){
        Log.d(TAG, "now here")
        viewModelScope.launch {
            Log.d(TAG, "now here??")
            withContext(Dispatchers.IO){
                Log.d(TAG, "now here????")
                try{
                    if(type.value.equals("available")){
                        val response = Api.retrofitService.getAvailable(accessToken.value!!)
                        Log.d(TAG, response.toString())
                        books.postValue(response.books)
                    }else if(type.value.equals("wanted")){
                        val response = Api.retrofitService.getWanted(accessToken.value!!)
                        Log.d(TAG, response.toString())
                        books.postValue(response.books)
                    }
                }catch (e: HttpException){
                    Log.d(TAG, "exceptioooon")
                }
            }
        }
    }

}