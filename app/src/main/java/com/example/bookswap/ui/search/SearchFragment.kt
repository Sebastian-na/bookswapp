package com.example.bookswap.ui.search

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import com.example.bookswap.LoginActivity
import com.example.bookswap.R
import com.example.bookswap.databinding.FragmentSearchBinding
import android.text.Editable




class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var _binding: FragmentSearchBinding
    val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        _binding.lifecycleOwner = viewLifecycleOwner
        _binding.viewModel = viewModel
        viewModel.accessToken.value = activity?.getSharedPreferences("user_information", Context.MODE_PRIVATE)?.getString(getString(R.string.access_token_key), "")
        viewModel.statusCode.observe(viewLifecycleOwner, {
            if(it == 403){
                val intent: Intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
            }
        })

        _binding.bookResults.adapter = BooksSearchedAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchQuery.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }
            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                Log.d("searchfragment", "hereeee")
                viewModel.query.value = s.toString()
                Log.d("searchfragment", viewModel.query.value.toString())
                viewModel.queryBooks()
            }
        })
    }

}