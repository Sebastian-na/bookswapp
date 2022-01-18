package com.example.bookswap.ui.profile

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.bookswap.R
import com.example.bookswap.databinding.FragmentProfileBinding
import com.google.android.material.tabs.TabLayoutMediator

class ProfileFragment : Fragment() {

    private lateinit var _binding: FragmentProfileBinding

    private val binding get() = _binding

    private lateinit var viewPager: ViewPager2

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        _binding.viewModel = viewModel
        _binding.lifecycleOwner = this
        viewModel.accessToken.value = activity?.getSharedPreferences("user_information", Context.MODE_PRIVATE)?.getString(getString(R.string.access_token_key), "")
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPager = _binding.booksViewPager
        val pagerAdapter = VPAdapter(this)
        viewPager.adapter = pagerAdapter
        viewModel.getUserData()
        val tabLayout = binding.booksTab
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            if(position == 0){
                tab.text = "Available"
            }else{
                tab.text = "Wanted"
            }
        }.attach()

        _binding.addBookButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_profile_to_addBookFragment)
        }
        super.onViewCreated(view, savedInstanceState)
    }

}