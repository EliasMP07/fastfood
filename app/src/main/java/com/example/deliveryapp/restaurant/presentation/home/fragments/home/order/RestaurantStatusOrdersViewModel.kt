package com.example.deliveryapp.restaurant.presentation.home.fragments.home.order

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.client.presentation.address.create.MVVMContract
import com.example.deliveryapp.client.presentation.address.create.MVVMDelegate
import com.example.deliveryapp.core.domain.model.Response
import com.example.deliveryapp.restaurant.domain.usecases.category.RestaurantUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantStatusOrdersViewModel @Inject constructor(
    private val restaurantUseCases: RestaurantUseCases
): ViewModel() , MVVMContract<RestaurantStatusOrderState, RestaurantStatusOrderAction> by MVVMDelegate(
    RestaurantStatusOrderState()
){
    override fun onAction(action: RestaurantStatusOrderAction) {

    }

    fun getOrders(status: String) {
        viewModelScope.launch {
            restaurantUseCases.getAllOrdersUseCase(status = status).collectLatest {
                updateUi {
                    when(it) {
                        is Response.Failure -> {
                            copy(
                                isLoading = false
                            )
                        }
                        Response.Loading -> {
                            copy(
                                isLoading = true
                            )
                        }
                        is Response.Success -> {
                            copy(
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