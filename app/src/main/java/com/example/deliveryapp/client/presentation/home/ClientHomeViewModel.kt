package com.example.deliveryapp.client.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.core.domain.model.User
import com.example.deliveryapp.core.domain.repository.SessionStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientHomeViewModel @Inject constructor(
    private val sessionStorage: SessionStorage
): ViewModel(){

    private val _state = MutableStateFlow(ClientHomeState())
    val state: StateFlow<ClientHomeState> get() = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {currentState->
                currentState.copy(
                    user =  sessionStorage.get()?: User()
                )
            }
        }
    }
}