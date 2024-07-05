package com.example.deliveryapp.auth.presentation.intro

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.deliveryapp.auth.presentation.login.LoginActivity
import com.example.deliveryapp.auth.presentation.register.RegisterActivity
import com.example.deliveryapp.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding

    companion object {
        fun create(context: Context): Intent =
            Intent(context, IntroActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
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