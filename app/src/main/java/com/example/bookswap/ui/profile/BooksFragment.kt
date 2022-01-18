package com.example.bookswap.ui.profile

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.bookswap.R
import com.example.bookswap.databinding.FragmentBooksBinding
import com.example.bookswap.model.Book
import com.example.bookswap.network.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


const val TAGS = "booksfragment"
class BooksFragment : Fragment() {

    private lateinit var _binding: FragmentBooksBinding
    private val viewModel: BooksViewModel by viewModels()
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBooksBinding.inflate(inflater, container, false)
        Log.d(TAGS, "hello from you know")
        val type = arguments?.getString("type")
        viewModel.type.value = type
        viewModel.accessToken.value = activity?.getSharedPreferences("user_information", Context.MODE_PRIVATE)?.getString(getString(R.string.access_token_key), "")
        _binding.viewModel = viewModel
        _binding.lifecycleOwner = this
        _binding.list.adapter = BooksAdapter(this)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getAllBooks()
        super.onViewCreated(view, savedInstanceState)
    }

    fun deleteBook(book: Book){
        lifecycleScope.launch{
            withContext(Dispatchers.IO){
                try{
                    if(book.owner.isNullOrBlank()){
                        val response = Api.retrofitService.deleteWanted(viewModel.accessToken.value!!, book._id)
                        Log.d("booksfragment", response.message)
                    }else{
                        val response = Api.retrofitService.deleteAvailable(viewModel.accessToken.value!!, book._id)
                        Log.d("booksfragment", response.message)
                    }
                }catch(e: Exception){
                    Log.d("booksfragment", e.localizedMessage)
                }
            }
        }
    }
}