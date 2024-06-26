package com.example.deliveryapp.auth.presentation.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.deliveryapp.R
import com.example.deliveryapp.auth.presentation.register.RegisterActivity
import com.example.deliveryapp.core.presentation.designsystem.dialog.DialogFragmentLauncher
import com.example.deliveryapp.core.presentation.designsystem.dialog.ErrorDialog
import com.example.deliveryapp.core.presentation.designsystem.dialog.ex.show
import com.example.deliveryapp.core.presentation.ui.ex.clearFocusFromAllFields
import com.example.deliveryapp.core.presentation.ui.startActivityWithFinish
import com.example.deliveryapp.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by viewModels()

    companion object {
        fun create(context: Context): Intent =
            Intent(context, LoginActivity::class.java)

    }

    @Inject
    lateinit var dialogLauncher: DialogFragmentLauncher


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUi()
    }

    private fun initUi() {
        initListeners(
            onAction = { action ->
                when (action) {
                    LoginAction.OnRegisterClick -> goToRegister()
                    else -> Unit
                }
                viewModel.onAction(action)
            }
        )
        initObservers()
        updateUI()

    }

    private fun updateUI() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.events.collect{event ->
                    when(event){
                        is LoginEvent.Error -> {
                            showErrorDialog(
                                error = event.error,
                                title = getString(R.string.error_login)
                            )
                        }
                        LoginEvent.Success -> {
                            goToRegister()
                        }
                    }
                }
            }
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    binding.btnLogin.isEnabled = it.canLogged
                    binding.pgLoading.isVisible = it.isLoading
                }
            }
        }
    }

    private fun clearFocusFromAllFields() {
        binding.root.clearFocusFromAllFields(
            binding.tieEmail,
            binding.tiePassword
        )
    }

    private fun showErrorDialog(
        error: String,
        title: String
    ) {
        ErrorDialog.create(
            title = title,
            description = error,
            negativeAction = ErrorDialog.Action(getString(R.string.cancelar_button_text)) {
                it.dismiss()
            },
            positiveAction = ErrorDialog.Action(getString(R.string.retry_login)) {
                viewModel.onAction(LoginAction.OnLoginClick)
                clearFocusFromAllFields()
                it.dismiss()
            }
        ).show(dialogLauncher, this)
    }


    private fun initListeners(
        onAction: (LoginAction) -> Unit
    ) {
        binding.tiePassword.doOnTextChanged { text, _, _, _ ->
            onAction(LoginAction.OnPasswordChange(text.toString()))
        }
        binding.tieEmail.doOnTextChanged { text, _, _, _ ->
            onAction(LoginAction.OnEmailChange(text.toString()))
        }
        binding.btnLogin.setOnClickListener {
            clearFocusFromAllFields()
            onAction(LoginAction.OnLoginClick)
        }

        binding.tvRegister.setOnClickListener {
            onAction(LoginAction.OnRegisterClick)
        }

    }

    private fun goToRegister() {
        startActivityWithFinish(RegisterActivity.create(this))
    }

}
