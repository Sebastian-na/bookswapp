package com.example.bookswap

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.bookswap.ui.login.ApiRequestStatus

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