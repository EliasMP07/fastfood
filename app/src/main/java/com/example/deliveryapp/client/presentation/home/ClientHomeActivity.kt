package com.example.deliveryapp.client.presentation.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.deliveryapp.R
import com.example.deliveryapp.databinding.ActivityClientHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ClientHomeActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    private val viewModel: ClientHomeViewModel by viewModels()

    private lateinit var binding: ActivityClientHomeBinding

    companion object {
        fun create(context: Context): Intent =
            Intent(context, ClientHomeActivity::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUi()
    }

    private fun initUi() {
        initNavigation()
        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collect{
                    loadImagePhoto(it.user.image)
                }
            }
        }
    }

    private fun loadImagePhoto(image: String){
        Glide.with(this@ClientHomeActivity).load(image).into(binding.ivProfilePhoto)
    }
    private fun initNavigation() {
        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHost.navController
        binding.bottomNavigation.setupWithNavController(navController)
    }
}