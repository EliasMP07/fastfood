package com.example.deliveryapp.client.presentation.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.deliveryapp.R
import com.example.deliveryapp.databinding.ActivityClientHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClientHomeActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    private lateinit var binding: ActivityClientHomeBinding

    companion object {
        fun create(context: Context): Intent =
            Intent(context, ClientHomeActivity::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.getInsetsController(this.window, this.window.decorView).isAppearanceLightStatusBars = false
        binding = ActivityClientHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUi()
    }

    private fun initUi() {
        initNavigation()
    }


    private fun initNavigation() {
        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHost.navController
        binding.bottomNavigation.setupWithNavController(navController)
    }
}