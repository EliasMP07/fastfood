package com.example.deliveryapp.delivery.presentation.mapDelivery

import androidx.lifecycle.ViewModel
import com.example.deliveryapp.core.domain.model.order.Order
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DeliveryMapViewModel @Inject constructor(
) : ViewModel() {

    private val _state = MutableStateFlow(DeliveryMapState())
    val state: StateFlow<DeliveryMapState> get() = _state.asStateFlow()

    fun insertOderState(order: Order){
        _state.update {
            it.copy(
                order = order
            )
        }
    }

}
