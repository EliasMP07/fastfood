package com.example.deliveryapp.auth.presentation.intro

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
class IntroViewModel @Inject constructor(
    private val sessionStorage: SessionStorage
): ViewModel() {

    private val _state = MutableStateFlow(IntroState())
    val state: StateFlow<IntroState> get() = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {currentState ->
                currentState.copy(
                    isLoggedIn = sessionStorage.get() != null,
                    user = sessionStorage.get()
                )
            }
        }
    }
}