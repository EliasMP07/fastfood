package com.example.deliveryapp.auth.presentation.register


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.auth.domain.model.RegisterRequest
import com.example.deliveryapp.auth.domain.usecases.AuthUseCases
import com.example.deliveryapp.auth.domain.validation.UserDataValidator
import com.example.deliveryapp.core.domain.model.Response
import com.example.deliveryapp.core.presentation.ui.utils.imageCamara
import com.example.deliveryapp.core.presentation.ui.utils.reduceImageSize
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
 * ViewModel para  el registro
 *
 * @param userDataValidator Validador de la contraseña y password ingresadas
 * @param authUseCases Contenedor de caso de uso de registro
 */
@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userDataValidator: UserDataValidator,
    private val authUseCases: AuthUseCases
) : ViewModel() {

    //Variable que contiene los estados de la Ui
    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> get() = _state.asStateFlow()

    //Variable que contiene los eventos del registro
    private val eventChannel = Channel<RegisterEvent>()
    val events = eventChannel.receiveAsFlow()


    //Funcion que valida las acciones en la UI
    fun onAction(
        action: RegisterAction
    ) {
        when (action) {
            is RegisterAction.OnEmailChange -> {
                _state.update { currentState ->
                    currentState.copy(
                        email = action.email,
                        canRegister = validForm(
                            email = action.email,
                            passwordValid = currentState.passwordValidationState.isValidPassword
                        ),
                    )
                }
            }

            is RegisterAction.OnLastNameChange -> {
                _state.update { currentState ->
                    currentState.copy(
                        lastName = action.lastName,
                        canRegister = validForm(
                            email = currentState.email,
                            passwordValid = currentState.passwordValidationState.isValidPassword
                        )
                    )
                }
            }

            is RegisterAction.OnNameChange -> {
                _state.update { currentState ->
                    currentState.copy(
                        name = action.name,
                        canRegister = validForm(
                            email = currentState.email,
                            passwordValid = currentState.passwordValidationState.isValidPassword
                        )
                    )
                }
            }

            is RegisterAction.OnPasswordChange -> {
                val passwordValid = userDataValidator.validatePassword(action.password)
                _state.update { currentState ->
                    currentState.copy(
                        password = action.password,
                        canRegister = validForm(
                            email = currentState.email,
                            passwordValid = passwordValid.isValidPassword
                        ),
                        passwordValidationState = passwordValid
                    )
                }
            }

            is RegisterAction.OnPhoneNumberChange -> {
                _state.update { currentState ->
                    currentState.copy(
                        phoneNumber = action.phoneNumber,
                        canRegister = validForm(
                            email = currentState.email,
                            passwordValid = currentState.passwordValidationState.isValidPassword
                        )
                    )
                }
            }

            RegisterAction.OnRegisterClick -> register()
            is RegisterAction.OnImageCamaraChange ->  {
                onCamaraSelected(action.image)
            }
            is RegisterAction.OnImageGalleryChange -> {
                onImageGalleryChange(action.image)
            }
            else -> Unit
        }
    }

    //Funcion que realiza el registro del usuario
    private fun register() {
        viewModelScope.launch {
            _state.update { currentState ->
                currentState.copy(
                    isLoading = true
                )
            }
            val result = authUseCases.register(
                registerRequest = RegisterRequest(
                    email = _state.value.email,
                    password = _state.value.password,
                    name = _state.value.name,
                    lastname = _state.value.lastName,
                    phone = _state.value.phoneNumber,
                    image = _state.value.image
                ),

            )
            when (result) {
                is Response.Failure -> {
                    _state.update { currentState ->
                        currentState.copy(
                            isLoading = false
                        )
                    }
                    eventChannel.send(RegisterEvent.Error(result.exception?.message.orEmpty()))
                }

                is Response.Success -> {
                    _state.update { currentState ->
                        currentState.copy(
                            isLoading = false
                        )
                    }
                    eventChannel.send(RegisterEvent.Success)
                }
                else -> Unit
            }
        }
    }

    //Funcion que valida la informacion el formulario y si true habilita el botom de registro
    private fun validForm(email: String, passwordValid: Boolean): Boolean {
        val validEmail = userDataValidator.isValidEmail(email)
        return validEmail && _state.value.name.isNotEmpty() && _state.value.lastName.isNotEmpty() && _state.value.phoneNumber.isNotEmpty() && passwordValid
    }

    private fun onCamaraSelected(image: String) {
        _state.update { currentState ->
            currentState.copy(
                imagePreview = image,
                image = imageCamara(image)
            )
        }
    }

    private fun onImageGalleryChange(image: String){
        _state.update { currentState ->
            currentState.copy(
                imagePreview = image,
                image = reduceImageSize(image)
            )
        }
    }
}
