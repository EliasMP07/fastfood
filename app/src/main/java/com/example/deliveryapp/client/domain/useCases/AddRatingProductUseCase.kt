package com.example.deliveryapp.client.domain.useCases

import com.example.deliveryapp.client.domain.repository.ClientRepository
import com.example.deliveryapp.core.domain.model.Response

class AddRatingProductUseCase(
    private val repository: ClientRepository
){
    suspend operator fun invoke(idProduct: String, rating: Double): Response<Unit>{
        return repository.addRatingProduct(idProduct, rating)
    }
}