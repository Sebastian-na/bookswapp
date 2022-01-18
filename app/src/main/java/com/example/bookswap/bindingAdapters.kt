package com.example.bookswap

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.bookswap.model.Book
import com.example.bookswap.model.Post
import com.example.bookswap.ui.home.WantedBooksAdapter
import com.example.bookswap.ui.login.ApiRequestStatus
import com.example.bookswap.ui.profile.BooksAdapter
import com.example.bookswap.ui.search.BooksSearchedAdapter
import com.example.bookswap.ui.search.PostAdapter

@BindingAdapter("ApiRequestStatus")
fun bindStatus(statusImageView: ImageView, status: ApiRequestStatus?) {
    when (status) {
        ApiRequestStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        ApiRequestStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        ApiRequestStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Book>?){
    val adapter = recyclerView.adapter as BooksSearchedAdapter
    adapter.submitList(data)
}

@BindingAdapter("postList")
fun bindPostRecyclerView(recyclerView: RecyclerView, data: List<Post>?){
    val adapter = recyclerView.adapter as PostAdapter
    adapter.submitList(data)
}

@BindingAdapter("wantedBooks")
fun bindWantedBookRecyclerView(recyclerView: RecyclerView, data: List<Book>?){
    val adapter = recyclerView.adapter as WantedBooksAdapter
    adapter.submitList(data)
}

@BindingAdapter("books")
fun bindBookRecyclerView(recyclerView: RecyclerView, data: List<Book>?){
    val adapter = recyclerView.adapter as BooksAdapter
    adapter.submitList(data)
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?){
    imgUrl?.let{
        val imgUri = imgUrl.toUri().buildUpon().scheme("http").build()
        imgView.load(imgUri){
            placeholder(R.drawable.loading_animation)
        }
    }
}