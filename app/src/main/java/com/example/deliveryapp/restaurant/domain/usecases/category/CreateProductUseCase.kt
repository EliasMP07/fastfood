package com.example.deliveryapp.restaurant.domain.usecases.category

import com.example.deliveryapp.core.domain.model.Response
import com.example.deliveryapp.restaurant.domain.repository.RestaurantRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CreateProductUseCase(
    private val repository: RestaurantRepository
) {
    suspend operator fun invoke(name: String, images:List<String>, price: Double, description: String, idCategory: String): Response<String> {
        return withContext(Dispatchers.IO){
            repository.createProduct(name = name, images = images, price = price, description = description, idCategory = idCategory)
        }
    }
}