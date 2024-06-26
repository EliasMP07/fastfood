
package com.example.deliveryapp.auth.presentation.register

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
import com.example.deliveryapp.auth.presentation.login.LoginActivity
import com.example.deliveryapp.core.presentation.designsystem.dialog.DialogFragmentLauncher
import com.example.deliveryapp.core.presentation.designsystem.dialog.ErrorDialog
import com.example.deliveryapp.core.presentation.designsystem.dialog.SuccessDialog
import com.example.deliveryapp.core.presentation.designsystem.dialog.ex.show
import com.example.deliveryapp.core.presentation.ui.ex.clearFocusFromAllFields
import com.example.deliveryapp.core.presentation.ui.ex.hideKeyboard
import com.example.deliveryapp.core.presentation.ui.startActivityWithFinish
import com.example.deliveryapp.databinding.ActivityRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private val viewModel: RegisterViewModel by viewModels()

    companion object {
        fun create(context: Context): Intent =
            Intent(context, RegisterActivity::class.java)
    }

    @Inject
    lateinit var dialogLauncher: DialogFragmentLauncher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUi()
    }

    private fun initUi() {
        initListeners(
            onAction = {action ->
                when(action){
                    RegisterAction.OnLoginClick -> goToLogin()
                    else -> Unit
                }
                viewModel.onAction(action)
            }
        )
        initObservers()
        updateUi()
    }

    //Funcion que escucha y actualiza la Ui dependiendo de los eventos
    private fun updateUi() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                viewModel.events.collect{event ->
                    when(event){
                        is RegisterEvent.Error -> {
                            showErrorDialog(error = event.message, title = getString(R.string.error))
                        }
                        RegisterEvent.Success -> {
                            showSuccessDialog()
                        }
                    }

                }
            }
        }
    }

    //Dialogo que se muestra la creacion de la cuenta fue exitosa
    private fun showSuccessDialog(){
        SuccessDialog.create(
            title = getString(R.string.success_create_account),
            description = getString(R.string.success_create_account_description),
            positiveAction = SuccessDialog.Action(getString(R.string.text_button_init)){
                goToHome()
                it.dismiss()
            }
        ).show(dialogLauncher, this)
    }

    //Dialogo que se muestra cuando hay un error en el registro
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
                viewModel.onAction(RegisterAction.OnRegisterClick)
                it.dismiss()
            }
        ).show(dialogLauncher, this)
    }

    /**
     * Funcion que contiene y escucha las acciones de los campos de texto y botones.
     *
     * @param onAction Lambda que recibe las acciones de los botones y campos de texto
     */
    private fun initListeners(
        onAction: (RegisterAction) -> Unit
    ) {
         binding.tieEmail.doOnTextChanged { text, _, _, _ ->
            onAction(RegisterAction.OnEmailChange(text.toString()))
         }
        binding.tiePassword.doOnTextChanged { text, _, _, _ ->
            onAction(RegisterAction.OnPasswordChange(text.toString()))
        }
        binding.tieName.doOnTextChanged { text, _, _, _ ->
            onAction(RegisterAction.OnNameChange(text.toString()))
        }
        binding.tieLastName.doOnTextChanged { text, _, _, _ ->
            onAction(RegisterAction.OnLastNameChange(text.toString()))
        }
        binding.tiePhoneNumber.doOnTextChanged { text, _, _, _ ->
            onAction(RegisterAction.OnPhoneNumberChange(text.toString()))
        }
        binding.btnRegister.setOnClickListener {
            clearFocusFromAllFields()
            onAction(RegisterAction.OnRegisterClick)
        }
        binding.tvLogin.setOnClickListener {
            onAction(RegisterAction.OnLoginClick)
        }

    }

    //Funcion que limpia el foco de los campos de texto
    private fun clearFocusFromAllFields() {
        binding.root.clearFocusFromAllFields(
            binding.tieEmail,
            binding.tiePassword,
            binding.tieName,
            binding.tieLastName,
            binding.tiePhoneNumber
        )
    }

    //Funcion que observa los cambios de los estados de la Ui
    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collect{
                    with(binding){
                        btnRegister.isEnabled = it.canRegister
                        pgLoading.isVisible= it.isLoading
                        hasNumber.isEnabled = it.passwordValidationState.hasNumber
                        hasMinLength.isEnabled = it.passwordValidationState.hasMinLength
                        hasLowerCase.isEnabled = it.passwordValidationState.hasLowerCaseCharacter
                        hasUpperCase.isEnabled = it.passwordValidationState.hasUpperCaseCharacter
                    }
                }
            }
        }
    }

    //Funcion que dirige a la pantalla de Home
    private fun goToHome(){
        startActivityWithFinish(LoginActivity.create(this))
    }

    //Funcion que dirige a la pantalla de Login
    private fun goToLogin(){
        startActivityWithFinish(LoginActivity.create(this))
    }

}