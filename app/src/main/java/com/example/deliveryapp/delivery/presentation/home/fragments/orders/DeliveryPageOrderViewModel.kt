package com.example.deliveryapp.delivery.presentation.home.fragments.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.client.presentation.address.create.MVVMContract
import com.example.deliveryapp.client.presentation.address.create.MVVMDelegate
import com.example.deliveryapp.core.domain.model.Response
import com.example.deliveryapp.delivery.domain.usecases.DeliveryUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeliveryPageOrderViewModel @Inject constructor(
    private val deliveryUseCases: DeliveryUseCases
): ViewModel(), MVVMContract<DeliveryPageOrderState, DeliveryPageOrderAction> by MVVMDelegate(DeliveryPageOrderState()){

    fun getOrderStatus(status: String){
        viewModelScope.launch {
            deliveryUseCases.getOrderDeliveryStatusUseCase(status = status).collectLatest {result ->
                updateUi {
                    when(result){
                        is Response.Failure -> {
                            copy(
                                orders = emptyList()
                            )
                        }
                        Response.Loading -> {
                            copy(
                                orders = emptyList()
                            )
                        }
                        is Response.Success -> {
                            copy(
                                orders = result.data
                            )
                        }
                    }
                }
            }
        }
    }
}