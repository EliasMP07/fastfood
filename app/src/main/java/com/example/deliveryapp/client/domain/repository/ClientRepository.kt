package com.example.deliveryapp.client.domain.repository

import com.example.deliveryapp.client.domain.model.Category
import com.example.deliveryapp.client.domain.model.ResponseClient
import kotlinx.coroutines.flow.Flow

interface ClientRepository {

    suspend fun getAllCategories(): Flow<ResponseClient<List<Category>>>
}