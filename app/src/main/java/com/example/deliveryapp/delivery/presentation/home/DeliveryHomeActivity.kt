package com.example.deliveryapp.delivery.presentation.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.deliveryapp.R
import com.example.deliveryapp.databinding.ActivityDeliveryHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeliveryHomeActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    private lateinit var binding: ActivityDeliveryHomeBinding

    companion object{
        fun create(context: Context): Intent = Intent(context, DeliveryHomeActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeliveryHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUi()
    }

    private fun initUi() {
        initNavigation()
    }

    private fun initNavigation() {
        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentDeliveryContainerView) as NavHostFragment
        navController = navHost.navController
        binding.bottomDeliveryNavigation.setupWithNavController(navController)
    }
}