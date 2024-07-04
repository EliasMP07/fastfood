package com.example.deliveryapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.core.user.domain.repository.SessionStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sessionStorage: SessionStorage
): ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> get() = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update { currentState ->
                currentState.copy(
                    isCheckAuth = true
                )
            }
            val user = sessionStorage.get()
            _state.update { currentState ->
                currentState.copy(
                    isLoggedIn = user != null,
                    user = user
                )
            }
            _state.update { currentState ->
                currentState.copy(
                    isCheckAuth = false
                )
            }
        }
    }
}