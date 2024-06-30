package com.example.deliveryapp.restaurant.presentation.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.deliveryapp.R
import com.example.deliveryapp.databinding.ActivityRestaurantHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RestaurantHomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRestaurantHomeBinding
    private lateinit var navController: NavController

    companion object{
        fun create(
            context: Context
        ): Intent = Intent(context, RestaurantHomeActivity::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUi()
    }

    private fun initUi() {
        initNavigation()
    }

    private fun initNavigation() {
        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentRestaurantContainerView) as NavHostFragment
        navController = navHost.navController
        binding.bottomRestaurantNavigation.setupWithNavController(navController)
    }
}