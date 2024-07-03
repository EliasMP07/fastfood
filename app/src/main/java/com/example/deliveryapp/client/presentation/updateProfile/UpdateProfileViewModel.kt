package com.example.deliveryapp.client.presentation.updateProfile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.R
import com.example.deliveryapp.core.domain.model.Response
import com.example.deliveryapp.core.presentation.ui.UiText
import com.example.deliveryapp.core.user.domain.model.User
import com.example.deliveryapp.core.presentation.ui.utils.imageCamara
import com.example.deliveryapp.core.presentation.ui.utils.reduceImageSize
import com.example.deliveryapp.core.user.domain.useCases.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateProfileViewModel @Inject constructor(
    private val userUseCase: UserUseCase
) : ViewModel() {

    private val eventChannel = Channel<UpdateProfileEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(UpdateProfileState())
    val state: StateFlow<UpdateProfileState> get() = _state

    fun insertUser(user: User) {
        _state.update { currentState ->
            currentState.copy(
                user = user
            )
        }
    }

    fun onAction(
        action: UpdateProfileAction
    ) {
        when (action) {
            is UpdateProfileAction.OnImageCamaraChange -> {
                onCamaraSelected(action.image)
            }

            is UpdateProfileAction.OnImageGalleryChange -> {
                onImageGalleryChange(action.image)
            }

            UpdateProfileAction.OnUpdateProfileClick -> update()
            is UpdateProfileAction.OnLastNameChange -> {
                _state.update { currentState ->
                    currentState.copy(
                        user = currentState.user.copy(
                            lastname = action.lastName
                        )
                    )
                }
            }

            is UpdateProfileAction.OnNameChange -> {
                _state.update { currentState ->
                    currentState.copy(
                        user = currentState.user.copy(
                            name = action.name
                        )
                    )
                }
            }

            is UpdateProfileAction.OnPhoneChange -> {
                _state.update { currentState ->
                    currentState.copy(
                        user = currentState.user.copy(
                            phone = action.phone
                        )
                    )
                }
            }

            else -> Unit
        }
    }

    private fun update() {
        viewModelScope.launch {
            _state.update { currentState ->
                currentState.copy(
                    isLoading = true,
                    user = currentState.user.copy(
                        image = if (currentState.imageUpload.isNotEmpty()) currentState.imageUpload else currentState.user.image,
                    )
                )
            }
            val result = userUseCase.updateProfileUseCase(
                _state.value.user
            )
            when (result) {
                is Response.Failure -> {
                    _state.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                        )
                    }
                    eventChannel.send(
                        UpdateProfileEvent.Error(
                           UiText.StringResource(
                                R.string.error_unknown
                            )
                        )
                    )
                }

                is Response.Success -> {
                    _state.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                        )
                    }
                    eventChannel.send(UpdateProfileEvent.Success(result.data))
                }
                else -> Unit
            }
        }
    }

    private fun onCamaraSelected(image: String) {
        _state.update { currentState ->
            currentState.copy(
                imageUpload = imageCamara(image),
                user = currentState.user.copy(
                    image = image
                )
            )
        }
    }

    private fun onImageGalleryChange(image: String) {
        _state.update { currentState ->
            currentState.copy(
                imageUpload = reduceImageSize(image),
                user = currentState.user.copy(
                    image = image,
                )
            )
        }
    }
}
