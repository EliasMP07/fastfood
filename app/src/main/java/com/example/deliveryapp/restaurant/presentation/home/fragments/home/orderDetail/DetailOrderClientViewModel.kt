package com.example.deliveryapp.restaurant.presentation.home.fragments.home.orderDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.R
import com.example.deliveryapp.auth.presentation.register.RegisterEvent
import com.example.deliveryapp.client.presentation.address.create.MVVMContract
import com.example.deliveryapp.client.presentation.address.create.MVVMDelegate
import com.example.deliveryapp.core.domain.model.Response
import com.example.deliveryapp.core.presentation.ui.UiText
import com.example.deliveryapp.restaurant.domain.model.OrderRestaurant
import com.example.deliveryapp.restaurant.domain.usecases.category.RestaurantUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailOrderClientViewModel @Inject constructor(
    private val restaurantUseCases: RestaurantUseCases
) : ViewModel(), MVVMContract<DetailOrderClientState, DetailOrderClientActions> by MVVMDelegate(
    DetailOrderClientState()
) {

    //Variable que contiene los eventos del registro
    private val eventChannel = Channel<DetailOrderClientEvent>()
    val events = eventChannel.receiveAsFlow()


    init {
        getDeliveriesAvailable()
    }

    fun getDeliveriesAvailable() {
        viewModelScope.launch {
            restaurantUseCases.getDeliveriesAvailableUseCase().collectLatest { result ->
                updateUi {
                    when (result) {
                        is Response.Failure -> {
                            copy(
                                deliveriesAvailable = emptyList()
                            )
                        }
                        Response.Loading -> {
                            copy(
                                deliveriesAvailable = emptyList()
                            )
                        }
                        is Response.Success -> {
                            copy(
                                deliveriesAvailable = result.data
                            )
                        }
                    }
                }
            }
        }
    }

    private fun assignDelivery(order: OrderRestaurant) {
        viewModelScope.launch {
            val result = restaurantUseCases.assignDeliveryUseCase(
                idOrder = order.id,
                idClient = order.idClient,
                idDelivery = uiState.value.idDelivery,
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

    override fun onAction(action: DetailOrderClientActions) {
        when(action){
            is DetailOrderClientActions.AssignDeliveryAction -> assignDelivery(order = action.order)
            is DetailOrderClientActions.OnDeliveryChange -> updateUi {
                copy(
                    idDelivery = action.deliveryId
                )
            }
        }
    }
}