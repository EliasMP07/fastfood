package com.example.deliveryapp.client.domain.repository

import com.example.deliveryapp.client.domain.model.Product
import com.example.deliveryapp.client.domain.model.Category
import com.example.deliveryapp.core.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface ClientRepository {
    suspend fun getProductByCategory(idCategory: String): Flow<Response<List<Product>>>
    suspend fun getAllCategories(): Flow<Response<List<Category>>>
}