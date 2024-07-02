package com.example.deliveryapp.restaurant.domain.repository

import com.example.deliveryapp.client.domain.model.Category
import com.example.deliveryapp.core.domain.model.Response
import com.example.deliveryapp.restaurant.domain.model.CategoryRequest
import kotlinx.coroutines.flow.Flow

interface RestaurantRepository {
    suspend fun createCategory(categoryRequest: CategoryRequest): Response<Unit>
    suspend fun getAllCategories(): Flow<List<Category>>
    suspend fun createProduct(name: String, images:List<String>, price: Double, description: String, idCategory: String): Response<String>
}