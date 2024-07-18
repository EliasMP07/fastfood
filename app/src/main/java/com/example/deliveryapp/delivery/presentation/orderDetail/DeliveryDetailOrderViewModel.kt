package com.example.deliveryapp.delivery.presentation.orderDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.R
import com.example.deliveryapp.core.domain.model.Response
import com.example.deliveryapp.core.domain.model.order.Order
import com.example.deliveryapp.core.presentation.ui.UiText
import com.example.deliveryapp.delivery.domain.usecases.DeliveryUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeliveryDetailOrderViewModel @Inject constructor(
    private val deliveryUseCases: DeliveryUseCases
): ViewModel() {


    //Variable que contiene los estados de la Ui
    private val _state = MutableStateFlow(DeliveryDetailOrderState())
    val state: StateFlow<DeliveryDetailOrderState> get() = _state.asStateFlow()

    private val eventChannel = Channel<DeliveryDetailOrderEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: DeliveryDetailOrderAction) {
        when(action){
            is DeliveryDetailOrderAction.InitDeliveryClick -> initDelivery(action.order)
        }
    }

    fun getOrder(order: Order){
        _state.update {currentState->
            currentState.copy(
                order = order
            )
        }
    }

    private fun initDelivery(order: Order){
        viewModelScope.launch {
            val result = deliveryUseCases.initDeliveryOrderUseCase(
                idOrder = order.id,
                idClient = order.idClient,
                idAddress = order.idAddress,
                status = order.status
            )
            when(result) {
                is Response.Failure -> {
                    eventChannel.send(
                        DeliveryDetailOrderEvent.Error(
                            UiText.StringResource(R.string.error_initdelivery)
                        )
                    )
                }
                is Response.Success -> {
                    _state.update {currentState->
                        currentState.copy(
                            order = currentState.order?.copy(
                                status = "EN CAMINO"
                            )
                        )
                    }
                    eventChannel.send(
                        DeliveryDetailOrderEvent.Success(
                            UiText.StringResource(R.string.success_initdelivery)
                        )
                    )
                }
                else -> Unit
            }
        }
    }
}