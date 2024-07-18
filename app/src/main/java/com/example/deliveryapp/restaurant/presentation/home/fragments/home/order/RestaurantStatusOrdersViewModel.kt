package com.example.deliveryapp.restaurant.presentation.home.fragments.home.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.core.domain.model.Response
import com.example.deliveryapp.restaurant.domain.usecases.category.RestaurantUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantStatusOrdersViewModel @Inject constructor(
    private val restaurantUseCases: RestaurantUseCases
): ViewModel() {

    //Variable que contiene los estados de la Ui
    private val _state = MutableStateFlow(RestaurantStatusOrderState())
    val state: StateFlow<RestaurantStatusOrderState> get() = _state.asStateFlow()

    fun getOrders(status: String) {
        viewModelScope.launch {
            restaurantUseCases.getAllOrdersUseCase(status = status).collectLatest {
                _state.update {currentState ->
                    when(it) {
                        is Response.Failure -> {
                            currentState.copy(
                                isLoading = false
                            )
                        }
                        Response.Loading -> {
                            currentState.copy(
                                isLoading = true
                            )
                        }
                        is Response.Success -> {
                            currentState.copy(
                                isLoading = false,
                                orders = it.data
                            )
                        }
                    }
                }
            }
        }
    }
}