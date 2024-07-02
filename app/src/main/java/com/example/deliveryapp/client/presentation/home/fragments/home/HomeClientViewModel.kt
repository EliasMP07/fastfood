package com.example.deliveryapp.client.presentation.home.fragments.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.client.domain.useCases.ClientUseCases
import com.example.deliveryapp.core.domain.model.Response
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
class HomeClientViewModel @Inject constructor(
    private val clientUseCases: ClientUseCases,
    private val sessionStorage: SessionStorage
): ViewModel(){

    private val _state = MutableStateFlow(HomeClientState())
    val state: StateFlow<HomeClientState> get() = _state.asStateFlow()

    init {
        getAllCategories()
        getUser()
    }

    private fun getUser(){
        viewModelScope.launch {
            _state.update {currentState ->
                currentState.copy(
                    user = sessionStorage.get()?: User()
                )
            }
        }
    }
    private fun getAllCategories(){
        viewModelScope.launch {
            clientUseCases.getAllCategoriesUseCase().collect{categoriesResponse->
                when(categoriesResponse){
                    is Response.Failure -> {
                        _state.update {currentState ->
                            currentState.copy(
                                isLoading = false,
                            )
                        }
                    }
                    Response.Loading -> {
                        _state.update {currentState ->
                            currentState.copy(
                                isLoading = true
                            )
                        }
                    }
                    is Response.Success -> {
                        _state.update {currentState ->
                            currentState.copy(
                                isLoading = false,
                                listCategories = categoriesResponse.data
                            )
                        }
                    }
                }
            }
        }
    }



}