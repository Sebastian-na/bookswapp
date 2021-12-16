package com.example.bookswap.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.bookswap.MainActivity
import com.example.bookswap.R
import com.example.bookswap.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment() {

    private lateinit var _binding: FragmentLoginBinding

    private val binding get() = _binding

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val activity = activity as AppCompatActivity
        activity.supportActionBar?.show()
        activity.supportActionBar?.setDisplayShowTitleEnabled(false)
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        _binding.lifecycleOwner = viewLifecycleOwner
        _binding.viewModel = viewModel

        viewModel.statusCode.observe(viewLifecycleOwner, Observer { statusCode ->
            if(statusCode == 200){
                val intent = Intent(activity, MainActivity::class.java)
                val sharedPref = activity?.getSharedPreferences("user_information", Context.MODE_PRIVATE)
                with (sharedPref.edit()){
                    putString(getString(R.string.access_token_key), "Bearer " + viewModel.accessToken.value)
                    apply()
                }
                startActivity(intent)
            }else if(statusCode == 401){
                Snackbar.make(binding.root, R.string.bad_credentials, Snackbar.LENGTH_SHORT).show()
            }
        })

        binding.noTienesCuenta.setOnClickListener{
            findNavController().navigate(R.id.action_login_to_registerFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Set color of part of the text in no_tienes_una_cuenta string
        val spannable = SpannableString(getString(R.string.no_tienes_una_cuenta))
        val strs = spannable.toString().split("?").toTypedArray()
        val register_text_color = ContextCompat.getColor(requireContext(), R.color.blue_500)
        spannable.setSpan(ForegroundColorSpan(register_text_color),  strs[0].length + 1, spannable.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.noTienesCuenta.text = spannable
        binding.loginButton.setOnClickListener {
            viewModel.login()
        }
    }

}