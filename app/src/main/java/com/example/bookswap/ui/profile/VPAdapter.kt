package com.example.bookswap.ui.profile
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter




class VPAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> {
                val fragment = BooksFragment()
                fragment.arguments = Bundle().apply{
                    putString("type", "available")
                }
                return fragment
            }
            1 -> {
                val fragment = BooksFragment()
                fragment.arguments = Bundle().apply{
                    putString("type", "wanted")
                }
                return fragment
            }
            else -> {
                val fragment = BooksFragment()
                fragment.arguments = Bundle().apply{
                    putString("type", "available")
                }
                return fragment
            }
        }
    }
}
