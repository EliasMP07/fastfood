package com.example.deliveryapp.restaurant.domain.repository

import com.example.deliveryapp.auth.domain.model.Response
import com.example.deliveryapp.restaurant.domain.model.Category
import com.example.deliveryapp.restaurant.domain.model.CategoryRequest

interface CategoryRepository {
    suspend fun createCategory(categoryRequest: CategoryRequest): Response<Unit>
}