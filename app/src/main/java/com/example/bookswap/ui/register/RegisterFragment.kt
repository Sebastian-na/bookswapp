package com.example.bookswap.ui.register

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.bookswap.R
import com.example.bookswap.databinding.FragmentLoginBinding
import com.example.bookswap.databinding.FragmentRegisterBinding
import com.google.android.material.snackbar.Snackbar

class RegisterFragment : Fragment() {

    private lateinit var _binding: FragmentRegisterBinding
    private val binding get() = _binding

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val activity = activity as AppCompatActivity
        activity.supportActionBar?.show()
        activity.supportActionBar?.setDisplayShowTitleEnabled(false)
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        _binding.lifecycleOwner = viewLifecycleOwner
        _binding.viewModel = viewModel
        viewModel.messageResponse.observe(viewLifecycleOwner, Observer { message ->
            if(message.isNotEmpty()){
                Snackbar.make(binding.root, message.toString(), Snackbar.LENGTH_LONG).show()
            }

        })

        binding.yaTienesCuenta.setOnClickListener{
            findNavController().navigate(R.id.action_registerFragment_to_login)
        }

        return binding.root
    }

}