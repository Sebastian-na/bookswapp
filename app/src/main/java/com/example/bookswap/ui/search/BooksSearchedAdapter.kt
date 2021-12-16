package com.example.bookswap.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bookswap.databinding.BookResultItemBinding
import com.example.bookswap.model.Book

class BooksSearchedAdapter: ListAdapter<Book, BooksSearchedAdapter.BookViewHolder>(DiffCallback){

    class BookViewHolder(private var binding: BookResultItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(book: Book){
            binding.book = book
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<Book>(){
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.photos[0] == newItem.photos[0]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return BookViewHolder(BookResultItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = getItem(position)
        holder.bind(book)
    }
}