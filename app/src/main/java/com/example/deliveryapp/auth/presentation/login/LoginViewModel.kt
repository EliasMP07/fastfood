package com.example.deliveryapp.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.auth.domain.validation.PatternValidator
import com.example.deliveryapp.auth.domain.model.Response
import com.example.deliveryapp.auth.domain.repository.AuthRepository
import com.example.deliveryapp.auth.domain.usecases.AuthUseCases
import com.example.deliveryapp.auth.domain.validation.UserDataValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel del inicio de sesión
 *
 * @param authUseCases Contenedor de los casos de uso de autenticacion
 * @param userDataValidator Validador del campo de Email
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    private val userDataValidator: UserDataValidator
): ViewModel() {

    /**
     * Variable que contiene los estados
     */
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> get() = _state.asStateFlow()

    /**
     * Variable que contiene los eventos de inición de sesión
     */
    private val eventChannel = Channel<LoginEvent>()
     val events = eventChannel.receiveAsFlow()

    /**
     * Funcion que valida el campo de texto de email y password
     */
    private fun validateLoginState(email: String, password: String): Boolean {
        val validEmail = userDataValidator.isValidEmail(email)
        return validEmail && password.isNotEmpty()
    }

    /**
     * Funcion que realiza el inicio de sesión
     */
    private fun login(){
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            val result = authUseCases.login(
                email = _state.value.email,
               password =  _state.value.password
            )

            when(result){
                is Response.Failure -> {
                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    eventChannel.send(LoginEvent.Error(result.exception?.message.orEmpty()))
                }
                is Response.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    eventChannel.send(LoginEvent.Success)
                }
            }
        }
    }

    /**
     * Funcion que valida las acciones en la UI
     */
    fun onAction(
        action: LoginAction
    ) {
        when (action) {
            LoginAction.OnLoginClick -> login()
            is LoginAction.OnEmailChange -> {
                _state.update { currentState ->
                    val newEmail = action.email
                    currentState.copy(
                        email = newEmail,
                        canLogged = validateLoginState(newEmail, currentState.password)
                    )
                }
            }
            is LoginAction.OnPasswordChange -> {
                _state.update { currentState ->
                    val newPassword = action.password
                    currentState.copy(
                        password = newPassword,
                        canLogged = validateLoginState(currentState.email, newPassword)
                    )
                }
            }
            else -> Unit
        }
    }
}
