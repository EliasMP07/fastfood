package com.example.deliveryapp.auth.presentation.intro

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.deliveryapp.R
import com.example.deliveryapp.auth.presentation.login.LoginActivity
import com.example.deliveryapp.auth.presentation.register.RegisterActivity
import com.example.deliveryapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: IntroViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collect{state ->
                    if (state.isLoggedIn){
                        goToLogin()
                    }
                }
            }
        }
        initUI()
    }

    private fun initUI() {
        initListeners(onAction = {action ->
            when(action){
                IntroAction.OnSignInClick -> goToLogin()
                IntroAction.OnSignUpClick -> goToSingIn()
            }
        })
    }

    private fun initListeners(onAction: (IntroAction) -> Unit) {
        with(binding){
            btnSignIn.setOnClickListener {
                onAction(IntroAction.OnSignInClick)
            }
            btnSignUp.setOnClickListener {
                onAction(IntroAction.OnSignUpClick)
            }
        }
    }
    private fun goToSingIn() {
        startActivity(RegisterActivity.create(this))
    }

    private fun goToLogin() {
        startActivity(LoginActivity.create(this))
    }
    private fun goToHome(){

    }
}