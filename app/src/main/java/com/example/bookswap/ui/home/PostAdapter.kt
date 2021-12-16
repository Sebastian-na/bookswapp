package com.example.bookswap.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bookswap.databinding.BookResultItemBinding
import com.example.bookswap.databinding.PostItemBinding
import com.example.bookswap.model.Book
import com.example.bookswap.model.Post
import com.example.bookswap.ui.home.WantedBooksAdapter

class PostAdapter: ListAdapter<Post, PostAdapter.PostHolder>(DiffCallback){

    class PostHolder(private var binding: PostItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post){
            binding.post = post
            binding.wantedBooks.adapter = WantedBooksAdapter()
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<Post>(){
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.photos[0] == newItem.photos[0]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        return PostHolder(PostItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}