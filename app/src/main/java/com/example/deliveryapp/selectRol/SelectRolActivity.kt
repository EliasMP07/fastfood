package com.example.deliveryapp.selectRol

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.deliveryapp.R
import com.example.deliveryapp.auth.presentation.login.LoginActivity
import com.example.deliveryapp.client.presentation.home.ClientHomeActivity
import com.example.deliveryapp.core.presentation.ui.startActivityWithFinish
import com.example.deliveryapp.databinding.ActivitySelectRolBinding
import com.example.deliveryapp.delivery.presentation.home.DeliveryHomeActivity
import com.example.deliveryapp.restaurant.presentation.home.RestaurantHomeActivity
import com.example.deliveryapp.selectRol.adapter.RolAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SelectRolActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectRolBinding
    private val viewModel: SelectRolViewModel by viewModels()

    companion object {
        fun create(context: Context): Intent =
            Intent(context, SelectRolActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectRolBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUi()
    }

    private fun initUi() {
        initList()
        initUiState()
        initListernes()
    }

    private fun initListernes() {
        binding.tvLogout.setOnClickListener {
            goToLogin()
        }
    }

    private fun initUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect{
                    (binding.rvRoles.adapter as RolAdapter).submitList(it.roles)
                    binding.tvTitle.text = getString(R.string.welcomeUser, it.user.name)
                }
            }
        }
    }

    private fun initList() {
        binding.rvRoles.apply {
            layoutManager = GridLayoutManager(this@SelectRolActivity, 2)
            adapter = RolAdapter(onRolSelected = {
                when{
                    it.name.contains("RESTAURANTE") -> goToRestaurant()
                    it.name.contains("REPARTIDOR") -> goToDelivery()
                    else -> goToClient()
                }
            })

        }
    }

    private fun goToLogin(){
        viewModel.logout()
        startActivityWithFinish(LoginActivity.create(this))
    }
    private fun goToClient(){
        startActivityWithFinish(ClientHomeActivity.create(this))
    }
    private fun goToRestaurant(){
        startActivityWithFinish(RestaurantHomeActivity.create(this))
    }
    private fun goToDelivery(){
        startActivityWithFinish(DeliveryHomeActivity.create(this))
    }
}