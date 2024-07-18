package com.example.deliveryapp.restaurant.presentation.home.restaurantOrderDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.R
import com.example.deliveryapp.core.domain.model.Response
import com.example.deliveryapp.core.domain.model.order.Order
import com.example.deliveryapp.core.presentation.ui.UiText
import com.example.deliveryapp.restaurant.domain.usecases.category.RestaurantUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailOrderClientViewModel @Inject constructor(
    private val restaurantUseCases: RestaurantUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(DetailOrderClientState())
    val state: StateFlow< DetailOrderClientState> get() = _state.asStateFlow()

    //Variable que contiene los eventos del registro
    private val eventChannel = Channel<DetailOrderClientEvent>()
    val events = eventChannel.receiveAsFlow()


    init {
        getDeliveriesAvailable()
    }

    fun getDeliveriesAvailable() {
        viewModelScope.launch {
            restaurantUseCases.getDeliveriesAvailableUseCase().collectLatest { result ->
                _state.update {currentState ->
                    when (result) {
                        is Response.Failure -> {
                            currentState.copy(
                                deliveriesAvailable = emptyList()
                            )
                        }
                        Response.Loading -> {
                            currentState.copy(
                                deliveriesAvailable = emptyList()
                            )
                        }
                        is Response.Success -> {
                            currentState.copy(
                                deliveriesAvailable = result.data
                            )
                        }
                    }
                }
            }
        }
    }

    private fun assignDelivery(order: Order) {
        viewModelScope.launch {
            val result = restaurantUseCases.assignDeliveryUseCase(
                idOrder = order.id,
                idClient = order.idClient,
                idDelivery = _state.value.idDelivery,
                idAddress = order.idAddress,
                status = order.status
            )
            when(result){
                is Response.Failure -> {
                    eventChannel.send(
                        DetailOrderClientEvent.Error(
                            UiText.StringResource(R.string.error_assing_delivery)
                        )
                    )
                }
                is Response.Success ->{
                    eventChannel.send(
                        DetailOrderClientEvent.Success(
                            UiText.StringResource(R.string.success_assing_delivery)
                        )
                    )
                }
                else -> Unit
            }
        }
    }

    fun onAction(action: DetailOrderClientActions) {
        when(action){
            is DetailOrderClientActions.AssignDeliveryAction -> assignDelivery(order = action.order)
            is DetailOrderClientActions.OnDeliveryChange ->{
                _state.update {currentState ->
                    currentState.copy(
                        idDelivery = action.deliveryId
                    )
                }
            }
        }
    }
}