package com.example.bookswap.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bookswap.databinding.ProfileBookItemBinding
import com.example.bookswap.model.Book
import com.example.bookswap.ui.add_book.AddBookFragment

class BooksAdapter(val fragment: BooksFragment): ListAdapter<Book, BooksAdapter.BookViewHolder>(DiffCallback)  {

    class BookViewHolder(private var binding: ProfileBookItemBinding): RecyclerView.ViewHolder(binding.root) {
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
            return oldItem.title + oldItem.author == newItem.title + newItem.author
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ProfileBookItemBinding.inflate(LayoutInflater.from(parent.context))
        binding.fragment = fragment
        binding.lifecycleOwner = fragment
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = getItem(position)
        holder.bind(book)
    }
}
