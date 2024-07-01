package com.example.deliveryapp.restaurant.domain.usecases.category

import com.example.deliveryapp.auth.domain.model.Response
import com.example.deliveryapp.restaurant.domain.model.CategoryRequest
import com.example.deliveryapp.restaurant.domain.repository.RestaurantRepository

class CreateCategoryUseCase (
    private val repository: RestaurantRepository
) {
    suspend operator fun invoke(categoryRequest: CategoryRequest): Response<Unit>{
        return repository.createCategory(categoryRequest)
    }
}