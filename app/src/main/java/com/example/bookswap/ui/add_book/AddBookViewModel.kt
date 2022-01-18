package com.example.bookswap.ui.add_book

import android.app.Application
import android.graphics.Bitmap
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookswap.model.Book
import com.example.bookswap.model.PostingBook
import com.example.bookswap.network.Api
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

enum class BookStatus {ADDED, ERROR}

class AddBookViewModel(application: Application): AndroidViewModel(application) {
    val title = MutableLiveData("")
    val author = MutableLiveData("")

    val genre = MutableLiveData("")
    val tags = MutableLiveData("")
    val description = MutableLiveData("")
    val isAvailable = MutableLiveData(true)
    val accessToken = MutableLiveData("")
    val userId = MutableLiveData("")
    val image = MutableLiveData<File>()
    val toastMessage = MutableLiveData<String>()
    val status = MutableLiveData<BookStatus>()

    fun addBook(){
        val tagsList = tags.value?.split(" ")
        if(title.value!!.isBlank() or author.value!!.isBlank() or genre.value!!.isBlank() or tags.value!!.isBlank() or description.value!!.isBlank()){
            toastMessage.value = "Missing fields"
            return
        }
        if(image.value == null){
            toastMessage.value = "An image is required"
            return
        }

        val MEDIA_TYPE_TEX = MediaType.parse("text/plain")
        val MEDIA_TYPE_IMAGE = MediaType.parse("image/*")

        viewModelScope.launch {
            withContext(Dispatchers.IO){
                try{
                    val imageFile = MultipartBody.Part.createFormData("image", image!!.value!!.name, RequestBody.create(MEDIA_TYPE_IMAGE, image.value!!))
                    val title = RequestBody.create(MEDIA_TYPE_TEX, title.value!!)
                    val author = RequestBody.create(MEDIA_TYPE_TEX, author.value!!)
                    val genre = RequestBody.create(MEDIA_TYPE_TEX, genre.value!!)
                    val description = RequestBody.create(MEDIA_TYPE_TEX, description.value!!)
                    val owner = RequestBody.create(MEDIA_TYPE_TEX, userId.value!!)
                    if(isAvailable.value == true){
                        val response = Api.retrofitService.addAvailable(accessToken = accessToken.value!!, image = imageFile, title = title, author=author, genre=genre, tags=tagsList!!, description = description, owner = owner)
                        Log.d("addbookviewmodel", response.toString())
                        status.postValue(BookStatus.ADDED)
                    }else{
                        val response = Api.retrofitService.addWanted(accessToken = accessToken.value!!, image = imageFile, title = title, author=author, genre=genre, tags=tagsList!!, description = description)
                        Log.d("addbookviewmodel", response.toString())
                        status.postValue(BookStatus.ADDED)
                    }
                }catch(e: Exception){
                    Log.d("addbookviewmodel", e.localizedMessage)
                    status.postValue(BookStatus.ERROR)
                }
            }
        }
    }

    fun setAvailable(bool: Boolean){
        isAvailable.value = bool
    }

}