package com.example.bookswap.ui.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookswap.model.Book
import com.example.bookswap.model.UserData
import com.example.bookswap.network.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class ProfileViewModel : ViewModel() {

    val name =  MutableLiveData("")
    val email = MutableLiveData("")
    val swaps = MutableLiveData(0)
    val friends = MutableLiveData(0)
    val bio = MutableLiveData("")
    val accessToken = MutableLiveData("")
    val profilePic = MutableLiveData("")

    fun getUserData(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                try{
                    val response = Api.retrofitService.getUser( accessToken = accessToken.value!!)
                    Log.d("profileviewmodel", response.toString())
                    name.postValue(response.name)
                    email.postValue(response.email)
                    swaps.postValue(response.swaps.size)
                    friends.postValue(response.friends.size)
                    if(response.bio.isEmpty()){
                        bio.postValue("Añade una descripción")
                    }else{
                        bio.postValue(response.bio)
                    }
                    profilePic.postValue(response.profilePic)
                }catch(e: HttpException){
                    Log.d("profileviewmodel", e.localizedMessage)
                }

            }
        }
    }
}