package com.example.deliveryapp.auth.presentation.intro

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.deliveryapp.R
import com.example.deliveryapp.auth.presentation.login.LoginActivity
import com.example.deliveryapp.auth.presentation.register.RegisterActivity
import com.example.deliveryapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
}