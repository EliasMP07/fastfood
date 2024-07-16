package com.example.deliveryapp.restaurant.domain.usecases.category

import com.example.deliveryapp.core.domain.model.Response
import com.example.deliveryapp.core.domain.model.order.Order
import com.example.deliveryapp.restaurant.domain.repository.RestaurantRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class GetAllOrdersUseCase(
    private val repository: RestaurantRepository
) {
    suspend operator fun invoke(status: String): Flow<Response<List<Order>>>{
        return withContext(Dispatchers.IO){
            repository.getAllOrders(status = status)
        }
    }
}