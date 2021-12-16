package com.example.bookswap.ui.login

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookswap.model.User
import com.example.bookswap.network.Api
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

enum class ApiRequestStatus { LOADING, ERROR, DONE }

class LoginViewModel : ViewModel() {
    val status = MutableLiveData<ApiRequestStatus>()

    val email = MutableLiveData<String>("")
    val password = MutableLiveData<String>("")
    val accessToken = MutableLiveData<String>()
    val statusCode = MutableLiveData<Int>()

    fun login() {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                status.postValue(ApiRequestStatus.LOADING)
                try{
                    val response = Api.retrofitService.login(User("", email.value!!, password.value!!))
                    accessToken.postValue(response.token!!)
                    status.postValue(ApiRequestStatus.DONE)
                    statusCode.postValue(200)
                }catch(e: retrofit2.HttpException){
                    statusCode.postValue(e.code())
                    status.postValue(ApiRequestStatus.DONE)
                }
            }
        }
    }
}