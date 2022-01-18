package com.example.bookswap

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.bookswap.databinding.ActivityLoginBinding

class LoginActivity: AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var navController: NavController
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPref = getSharedPreferences("user_information", Context.MODE_PRIVATE)
        val accessToken = sharedPref.getString(getString(R.string.access_token_key), "")
        val userId = sharedPref.getString(getString(R.string.user_id_key), "")
        if(userId!!.isEmpty()){
            //do nothing
        }
        viewModel.accessToken.value = accessToken
        viewModel.isTokenOk.observe(this, {
            if(it == IsTokenOK.NO){
                //do nothing
            }else{
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        })
        viewModel.verifyToken()
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.nav_host_fragment_activity_login)
        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}