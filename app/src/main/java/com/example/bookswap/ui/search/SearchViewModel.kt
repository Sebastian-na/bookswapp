package com.example.bookswap.ui.search
import android.util.Log
import androidx.lifecycle.*
import com.example.bookswap.model.Book
import com.example.bookswap.network.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SearchViewModel : ViewModel() {
    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> = _books

    val query = MutableLiveData<String>("")

    val accessToken = MutableLiveData<String>("")

    val statusCode = MutableLiveData<Int>()


    fun queryBooks(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                try{
                    val response = Api.retrofitService.queryBooks(query.value!!, accessToken = accessToken.value!!)
                    _books.postValue(response)
                }catch (e: retrofit2.HttpException){
                    if(e.code() == 403){
                        statusCode.postValue(403)
                    }
                }
            }
        }
    }
}