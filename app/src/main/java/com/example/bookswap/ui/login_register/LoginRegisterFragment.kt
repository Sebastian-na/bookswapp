package com.example.bookswap.ui.login_register
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.bookswap.R
import com.example.bookswap.databinding.FragmentLoginRegisterBinding

class LoginRegisterFragment : Fragment() {

    private lateinit var _binding: FragmentLoginRegisterBinding

    private val binding get() = _binding

    companion object {
        fun newInstance() = LoginRegisterFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val activity = activity as AppCompatActivity
        activity.supportActionBar?.setDisplayShowTitleEnabled(false)
        _binding = FragmentLoginRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginRegisterFragment_to_login)
        }
        binding.registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginRegisterFragment_to_registerFragment)
        }
    }


}