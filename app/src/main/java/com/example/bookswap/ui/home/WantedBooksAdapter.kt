package com.example.bookswap.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bookswap.databinding.WantedBookItemBinding
import com.example.bookswap.model.Book

class WantedBooksAdapter: ListAdapter<Book, WantedBooksAdapter.BookViewHolder>(DiffCallback) {
    class BookViewHolder(private var binding: WantedBookItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(book: Book){
            binding.wantedBook = book
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<Book>(){
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.title + oldItem.author == newItem.title + newItem.author
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return BookViewHolder(WantedBookItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
       val book = getItem(position)
        holder.bind(book)
    }
}