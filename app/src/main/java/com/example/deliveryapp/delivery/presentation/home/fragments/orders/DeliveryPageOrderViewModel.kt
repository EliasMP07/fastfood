package com.example.deliveryapp.delivery.presentation.home.fragments.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.core.domain.model.Response
import com.example.deliveryapp.delivery.domain.usecases.DeliveryUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeliveryPageOrderViewModel @Inject constructor(
    private val deliveryUseCases: DeliveryUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(DeliveryPageOrderState())
    val state: StateFlow<DeliveryPageOrderState> get() = _state.asStateFlow()


    fun getOrderStatus(status: String) {
        viewModelScope.launch {
            deliveryUseCases.getOrderDeliveryStatusUseCase(status = status).collectLatest { result ->
                _state.update { currentState ->
                    when (result) {
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
                                orders = result.data
                            )
                        }
                    }
                }
            }
        }
    }
}