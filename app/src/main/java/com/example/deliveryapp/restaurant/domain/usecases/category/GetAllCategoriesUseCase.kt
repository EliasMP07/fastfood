package com.example.deliveryapp.restaurant.domain.usecases.category

import com.example.deliveryapp.client.domain.model.Category
import com.example.deliveryapp.restaurant.domain.repository.RestaurantRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class GetAllCategoriesUseCase (
    private val repository: RestaurantRepository
) {
    suspend operator fun invoke(): Flow<List<Category>>{
        return withContext(Dispatchers.IO){
            repository.getAllCategories()
        }
    }
}