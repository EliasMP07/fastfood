package com.example.deliveryapp.client.presentation.home.fragments.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.client.domain.useCases.ClientUseCases
import com.example.deliveryapp.core.domain.model.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientOrderStatusViewModel @Inject constructor(
    private val clientUseCases: ClientUseCases
) : ViewModel() {

    //Variable que contiene los estados de la Ui
    private val _state = MutableStateFlow(ClientOrderStatusState())
    val state: StateFlow<ClientOrderStatusState> get() = _state.asStateFlow()

    fun getMyOrders(status: String) {
        viewModelScope.launch {
            clientUseCases.getStatusOrdersUseCase(status = status).collect { result ->
                _state.update {
                    when (result) {
                        is Response.Failure -> {
                            it.copy(
                                isLoading = false
                            )
                        }
                        Response.Loading -> {
                            it.copy(
                                isLoading = false
                            )
                        }
                        is Response.Success -> {
                            it.copy(
                                listOrders = result.data
                            )
                        }
                    }
                }
            }
        }
    }
}