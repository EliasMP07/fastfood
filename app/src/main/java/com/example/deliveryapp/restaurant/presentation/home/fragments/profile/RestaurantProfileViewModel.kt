package com.example.deliveryapp.restaurant.presentation.home.fragments.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.core.user.domain.model.User
import com.example.deliveryapp.core.user.domain.repository.SessionStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantProfileViewModel @Inject constructor(
    private val sessionStorage: SessionStorage
): ViewModel(){

    private val _state = MutableStateFlow(RestaurantProfileState())
    val state: StateFlow<RestaurantProfileState> get() = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getUser()
        }
    }

    fun getUser(){
        viewModelScope.launch {
            _state.update {currentState->
                currentState.copy(
                    user = sessionStorage.get()?: User()
                )
            }
        }
    }

    fun logout(){
        viewModelScope.launch {
            sessionStorage.set(null)
        }
    }

}