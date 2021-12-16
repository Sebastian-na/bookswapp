package com.example.bookswap.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.bookswap.LoginActivity
import com.example.bookswap.R
import com.example.bookswap.databinding.FragmentHomeBinding
import com.example.bookswap.databinding.FragmentSearchBinding
import com.example.bookswap.ui.login.LoginViewModel
import com.example.bookswap.ui.search.PostAdapter

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var _binding: FragmentHomeBinding

    private val binding get() = _binding !!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setHasOptionsMenu(true)
        _binding.lifecycleOwner = viewLifecycleOwner
        _binding.viewModel = viewModel
        viewModel.accessToken.value = activity?.getSharedPreferences("user_information", Context.MODE_PRIVATE)?.getString(getString(R.string.access_token_key), "")
        viewModel.statusCode.observe(viewLifecycleOwner, {
            if(it == 403){
                val intent: Intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
            }
        })

        _binding.postsFeed.adapter = PostAdapter()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getFeed()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.action_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search -> {
                findNavController().navigate(R.id.action_navigation_home_to_searchFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}