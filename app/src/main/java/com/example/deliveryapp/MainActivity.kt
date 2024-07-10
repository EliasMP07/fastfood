package com.example.deliveryapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.deliveryapp.auth.presentation.intro.IntroActivity
import com.example.deliveryapp.client.presentation.home.ClientHomeActivity
import com.example.deliveryapp.core.presentation.ui.startActivityWithFinish
import com.example.deliveryapp.selectRol.SelectRolActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.state.value.isCheckAuth
            }
        }
        overridePendingTransition(0, 0)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                withContext(Dispatchers.Main.immediate){
                    viewModel.state.collect { state ->
                        if (!state.isCheckAuth) {
                            if (state.isLoggedIn) {
                                if (viewModel.state.value.user?.roles?.size!! > 1) {
                                    goToSelectRol()
                                } else {
                                    goToHomeClient()
                                }
                            } else {
                                goToIntro()
                            }
                        }
                    }
                }
            }
        }

    }


    private fun goToIntro() {
        startActivityWithFinish(IntroActivity.create(this))
    }

    private fun goToSelectRol() {
        startActivityWithFinish(SelectRolActivity.create(this))
    }

    private fun goToHomeClient() {
        startActivityWithFinish(ClientHomeActivity.create(this))
    }


}