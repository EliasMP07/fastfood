package com.example.deliveryapp.restaurant.domain.repository

import com.example.deliveryapp.client.domain.model.Category
import com.example.deliveryapp.core.domain.model.Response
import com.example.deliveryapp.restaurant.domain.model.CategoryRequest
import com.example.deliveryapp.restaurant.domain.model.DeliveryAvailable
import com.example.deliveryapp.restaurant.domain.model.OrderRestaurant
import kotlinx.coroutines.flow.Flow

interface RestaurantRepository {
    suspend fun createCategory(categoryRequest: CategoryRequest): Response<Unit>
    suspend fun getAllCategories(): Flow<List<Category>>
    suspend fun createProduct(name: String, images:List<String>, price: Double, description: String, idCategory: String): Response<String>
    suspend fun getAllOrders(status: String): Flow<Response<List<OrderRestaurant>>>
    suspend fun getAvailableDelivery(): Flow<Response<List<DeliveryAvailable>>>
    suspend fun assignDelivery(idOrder: String, idClient: String, idAddress: String, idDelivery: String, status: String): Response<Unit>
}