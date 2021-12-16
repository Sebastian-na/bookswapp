package com.example.bookswap

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookswap.network.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

enum class IsTokenOK {YES, NO}

class LoginViewModel: ViewModel() {

    val accessToken = MutableLiveData<String>("")

    val isTokenOk = MutableLiveData<IsTokenOK>()

    fun verifyToken(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                try{
                    val response = Api.retrofitService.verifyToken(accessToken.value!!)
                    isTokenOk.postValue(IsTokenOK.YES)
                }catch (E: Exception){
                    isTokenOk.postValue(IsTokenOK.NO)
                }

            }
        }
    }
}