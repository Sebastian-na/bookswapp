package com.example.bookswap.ui.register

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookswap.R
import com.example.bookswap.model.User
import com.example.bookswap.network.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.regex.Matcher
import java.util.regex.Pattern

const val TAG = "RegisterViewModel"

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    val name = MutableLiveData<String>("")
    val email = MutableLiveData<String>("")
    val password = MutableLiveData<String>("")
    val password2 = MutableLiveData<String>("")
    val messageResponse = MutableLiveData("")


    fun register(){
        if(!email.value?.contains("@unal.edu.co")!!){
            messageResponse.postValue(getString(R.string.use_unal_email))
            return
        }
        if(!password.value.equals(password2.value)){
            messageResponse.postValue(getString(R.string.password_not_equal))
            return
        }
        if(password.value!!.length < 8){
            messageResponse.postValue(getString(R.string.password_too_short))
            return
        }
        if(!isPasswordValid(password.value!!)){
            messageResponse.postValue(getString(R.string.password_should_contain))
            return
        }

        val user = User(name.value, email.value!!, password.value!!)

        viewModelScope.launch {
            withContext(Dispatchers.IO){
                try{
                    val response = Api.retrofitService.register(user)
                    messageResponse.postValue(response.message.toString())
                }catch(e: retrofit2.HttpException){
                    messageResponse.postValue(getString(R.string.user_already_created))
                }

            }
        }
    }

    private fun isPasswordValid(pass: String): Boolean {
        val regex = ("^(?=.*[a-z])(?=."
                + "*[A-Z])(?=.*\\d)"
                + "(?=.*[-+_!@#$%^&*., ?]).+$")
        val p: Pattern = Pattern.compile(regex)
        val m: Matcher = p.matcher(pass)
        return m.matches()
    }

    private fun getString(id: Int): String{
        return getApplication<Application>().resources.getString(id)
    }
}