package com.example.deliveryapp.restaurant.presentation.home.fragments.home.order

import androidx.lifecycle.ViewModel
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

    fun getOrders(status: String){
        viewModelScope.launch {
            restaurantUseCases.getAllOrdersUseCase(status = status).collectLatest {
                updateUi {
                    when(it){
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
                                orders = it.data
                            )
                        }
                    }
                }
            }
        }
    }
}